angular.module('memoriseApp', ['ngRoute', 'ngCookies', 'memoriseApp.services'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});

			$routeProvider.when('/register', {
                templateUrl: 'partials/register.html',
                controller: RegisterController
            });

			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});

			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});

			$routeProvider.otherwise({
				templateUrl: 'partials/index.html',
				controller: IndexController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else if (status == 500) {
			        		    $rootScope.error = "User with the same name is already exist";
			        		}
			        		else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (memoriseAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore, UserService, $http) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};

		$rootScope.tryShowMemos = function() {
		    if($rootScope.hasRole('ROLE_USER')) {
		        $location.path("/");
		    }
		    else {
                $location.path("/login");
		    }
		}

		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};

		$rootScope.crawlBtnIsDisabled = false;
		$rootScope.Crawler = function() {
			$rootScope.crawlBtnIsDisabled = true;
		    $http.post('/rest/crawler/run')
		        .then(function success(response) {
		        	switch (response.status) {
		        		case 202:
		            		alert('Crawler start successfully');
		            		break;
		            	case 208:
		            		alert('Please wait, crawler already is being run');
		            		break;
		            	default:
		            		alert('Unknown non error response');
		            		break;
		            }
		    		$rootScope.crawlBtnIsDisabled = false;
		        }, function error(response) {
		            alert('Crawler error\nstatus: ' + response.status +
		                '\nand response: ' + response.statusText);
		    		$rootScope.crawlBtnIsDisabled = false;
		        });
		}
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	});

function IndexController($rootScope, $scope, MemoService) {

	$scope.memoEntries = MemoService.query().sort(function (a, b) {
        return (a.status > b.status) ? -1 : (a.status < b.status) ? 1 : 0;
    });

    $scope.checkboxStatusModel = {
           archived: !$rootScope.hasRole('ROLE_USER'),
           actual: $rootScope.hasRole('ROLE_USER'),
           candidate: $rootScope.hasRole('ROLE_PUBLISHER')
    };

    $scope.filterMemosByAllStatuses = function(allMemos) {
        for (status in $scope.checkboxStatusModel) {
            allMemos = !($scope.checkboxStatusModel[status]) ? allMemos.filter(function(obj) {
                return obj.status != status.toUpperCase();
            }) : allMemos;
        }
        return allMemos;
    };

	$scope.deleteEntry = function(memoEntry) {
		memoEntry.$remove(function() {
			$scope.memoEntries = MemoService.query();
		});
	};
};


function EditController($scope, $routeParams, $location, MemoService) {

	$scope.memoEntry = MemoService.get({id: $routeParams.id});

	$scope.statuses = ["ARCHIVED", "ACTUAL", "CANDIDATE"];

	$scope.save = function() {
		$scope.memoEntry.$save(function() {
			$location.path('/');
		});
	};
};

function CreateController($scope, $location, MemoService) {

	$scope.memoEntry = new MemoService();

	$scope.statuses = ["ARCHIVED", "ACTUAL", "CANDIDATE"];

	$scope.save = function() {
		$scope.memoEntry.$save(function() {
			$location.path('/');
		});
	};
};

function RegisterController($scope, $rootScope, $location, $cookieStore, UserService) {
	$scope.register = function() {
		UserService.register($.param({username: $scope.registerUsername, password: $scope.registerPassword}),
		    function() {
		        $location.path("/login");
		    }
		);
	};

    $scope.isUserEmpty = function() {
        return !$scope.registerUsername || !$scope.registerPassword;
    }
};

function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path("/");
			});
		});
	};
};


var services = angular.module('memoriseApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},

				register: {
                    method: 'POST',
                    params: {'action' : 'register'},
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
});

services.factory('MemoService', function($resource) {

	return $resource('rest/memo/:id', {id: '@id'});
});



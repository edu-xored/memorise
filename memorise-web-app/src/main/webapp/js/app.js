angular.module('medicalJournalApp', ['ngRoute', 'ngCookies', 'medicaljournalApp.services'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});

			$routeProvider.when('/upload/:id', {
				templateUrl: 'partials/upload.html',
				controller: UploadController
			});

			$routeProvider.when('/read/:id', {
				templateUrl: 'partials/read.html',
				controller: ReadController
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
			        		} else {
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
		        			if (medicalJournalAppConfig.useAuthTokenHeader) {
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
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};

		$rootScope.Crawler = function() {
		    $http.post('/rest/crawler/run');
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

function IndexController($scope, JournalService) {

	$scope.journalEntries = JournalService.query();

	$scope.deleteEntry = function(journalEntry) {
		journalEntry.$remove(function() {
			$scope.journalEntries = JournalService.query();
		});
	};
};


function EditController($scope, $routeParams, $location, JournalService, fileUpload) {

	$scope.journalEntry = JournalService.get({id: $routeParams.id});

	$scope.save = function() {
		$scope.journalEntry.$save(function() {
			$location.path('/');
		});
	};

	$scope.uploadFile = function(){
     var file = $scope.myFile;
     console.log('file is ' );
     console.dir(file);
     var uploadUrl = "/rest/file/" +  $routeParams.id + "/upload";
     fileUpload.uploadFileToUrl(file, uploadUrl);
 };

};


function ReadController($scope, $routeParams, $location, DownloadJournalContentService, JournalService) {

	$scope.journalEntry = JournalService.get({id: $routeParams.id});
	$scope.buildPDF = function() {
	                $http.get('/rest/' + $routeParams.id + '/download',{headers: {'AccessKeyId': 'accesskey'}, responseType: 'arraybuffer'})
	                    .success(function (data) {
	                        var file = new Blob([data], {type: 'application/pdf'});
	                        var fileURL = URL.createObjectURL(file);
	                        $window.open(fileURL);
	                    }
	                );
	            };
};

var app = angular.module('ngpdfviewerApp', [ 'ngPDFViewer' ]);
app.requires.push('ngPDFViewer');

app.controller('TestCtrl', [ '$scope', 'PDFViewerService', function($scope, pdf) {
    $scope.viewer = pdf.Instance("viewer");

    $scope.nextPage = function() {
        $scope.viewer.nextPage();
    };

    $scope.prevPage = function() {
        $scope.viewer.prevPage();
    };

    $scope.pageLoaded = function(curPage, totalPages) {
        $scope.currentPage = curPage;
        $scope.totalPages = totalPages;
    };
}]);

function UploadController($scope, $routeParams, $location, JournalService, fileUpload) {

	$scope.journalEntry = JournalService.get({id: $routeParams.id});

	$scope.uploadFile = function(){
     var file = $scope.myFile;
     console.log('file is ' );
     console.dir(file);
     var uploadUrl = "/rest/file/" +  $routeParams.id + "/upload";
     fileUpload.uploadFileToUrl(file, uploadUrl);
     $location.path('/');
 };
};


function CreateController($scope, $location, JournalService) {

	$scope.journalEntry = new JournalService();

	$scope.save = function() {
		$scope.journalEntry.$save(function() {
			$location.path('/');
		});
	};
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


var services = angular.module('medicaljournalApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

services.factory('JournalService', function($resource) {

	return $resource('rest/journal/:id', {id: '@id'});
});


services.factory('DownloadJournalContentService', function($resource) {

	return $resource('rest/file/:id/download', {id: '@id'});
});


services.factory('UploadJournalContentService', function($resource) {

	return $resource('rest/file/:id/upload', {id: '@id'});
});


services.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

services.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
		fd.append('fileSize', file.size);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
				//todo print ok message
        })
        .error(function(){
				//todo print error message
        });
    }
}]);



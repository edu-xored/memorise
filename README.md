# memorise

Project MemoRising is online service for looking for a memes that are becoming popular.

Mem - at this project is some new stable expression used at online social media (VC,)
##Basic cases 

Unregistered online user is able to 
* View old (last year top 10 mems)
* Register using email/login and password 
* Login using email/login and password 

Registered online user is able to 
* Request theme to find mems that are becoming popular (later if he has enough money)
* View actual last month top 10 mems


# Instalation and quick start 

Please use commands as folows to launch app (for linux, for windows it can base easily changed on appropriate command): 

git clone https://github.com/edu-xored/memorise
cd platform 
mvn install 
cd ../memorise-web-app
mvn install
mvn jetty:run

then open URL at browser: 
http://localhost:8080

You will see login page is everything was ok.

##Project technical details

## Components
### Platform 

Is base bootstrap project and common crosscuting (orm/). 

### Mmemorise Web application
* Frontend - HTML5 responsive UI using REST to communicate with backend 
* Backend
** REST Services
** Async processes using crowler to find already registered mems and cache some new mems
* DB Postgres
* NoSQL storage for cache of some new mems (in future), first time SpringCache can be used with external storage (Ehcache)

### Stack 

* maven
* Java 
* Spring 
* SpringCache
* Ehcache
* REST
* html4j
* crowler4j
* Hibernate
* In memory DB (HSQLDB) / Postgres

### Proposed Algorithm to find mems (Any others are welcome!)

* Get list of current mems (mems found at previose iteration) + new mem candidates based on requested themes or from cache
* Calculate velocity of growing the rate of growth in citation
** If velocity is growing with acceleration then it's rising mem
** else remove from candidates 



## Resources
TBD

# memorise

Project MemoRising is online service for looking for a memes that are becoming popular.

Meme - at this project is some new stable expression used at online social media (VC,)
##Basic cases 

Unregistered online user is able to 
* View old (last year top 10 memes)
* Register using email/login and password 
* Login using email/login and password 

Registered online user is able to 
* Request theme to find memes that are becoming popular (later if he has enough money)
* View actual last month top 10 memes


# Installation and quick start 

Please use commands as follows to launch app (for linux, for windows it can base easily changed on appropriate command): 

* git clone https://github.com/edu-xored/memorise
* mvn install 
* cd memorise-web-app
* mvn jetty:run
* then open URL at browser: 
http://localhost:8080

You will see login page if everything was ok.

##Project technical details

## Components
### Platform 

Is base bootstrap project and common crosscuting (orm/). 

### Memorise Web application
* Frontend - HTML5 responsive UI using REST to communicate with backend 
* Backend
** REST Services
** Async processes using crowler to find already registered memes and cache some new memes
* DB Postgres
* NoSQL storage for cache of some new memes (in future), first time SpringCache can be used with external storage (Ehcache)

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

### Proposed Algorithm to find memes (Any others are welcome!)

* Get list of current memes (memes found at previous iteration) + new meme candidates based on requested themes or from cache
* Calculate velocity of growing the rate of growth in citation
** If velocity is growing with acceleration then it's rising meme
** else remove from candidates 



## Resources
### Telegram group MemoRise.edu-xored:
Invite link: 
https://telegram.me/joinchat/C5PUiQpNvcHa9TE5EWuO5Q

### Opened issues
We use github issue tracker and github Projects to track issues progress:
https://github.com/edu-xored/memorise/issues
https://github.com/edu-xored/memorise/projects

## Project flow
### Looking for an open issues at github projects tracker:
https://github.com/edu-xored/memorise/projects

When you started working on issue assign it to yourself and move issue card to the In Progress column.

### Create Personal fork (top right button)
 
### Clone Personal fork 
for example 
git clone https://github.com/DVEfremov/edu-xored/memorise/issues


### Make some change and commit to personal fork/branch

git add <list of files or dirs> 


git commit


Use reference to issue # at commit message for example:

issue #2 initial web application bootstrap 

- you can provide some additional details as well 

git push origin <branch name>

### Create Merge request

Use "New pull request button" at https://github.com/edu-xored/memorise

 
### Ask other members to review your changes  

if all comments answered and all problems resolved some one who is responsible (lead of the group) submits the changes to master repository
### Only after that you close the issue 
Be close make you you have done:

* Provide basic case for you issue to reproduce at web front (like product owner case) 
* Provide unit tests for algorithms 
* Provide integration test for new components like crowler 
* Provide description of some other cases and change you have made in therms of Quality Assurance Engineer (I've changed the logic of memes searching)

### Move issue card to the Completed column when it's closed

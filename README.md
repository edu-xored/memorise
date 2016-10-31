[![Build Status](https://travis-ci.org/edu-xored/memorise.svg?branch=master)](https://travis-ci.org/edu-xored/memorise)

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

Итак, вот что нам нужно:
* JDK 1.8
* IDE (InelliJ IDEA or Eclipse)
* Maven 3.2+
* Git

## Установка JDK

Скачиваем актуальную версию JDK для вашей операционной системы с сайта Oracle:
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Запускаем исполняемый файл и следуем указаниям мастера установки.

## Установка IDE

Скачиваем IntelliJ Idea Community 
https://www.jetbrains.com/idea/download/

Запускаем исполняемый файл и следуем указаниям мастера установки.
При первом запуске мастер предложит вам сконфигурировать IDE. Оставляем все по умолчанию. 

Мы могли бы на этом остановиться, так как IDE поставляется с git и Maven, но давайте рассмотрим установку и настройку всех инструментов.

## Установка Maven

Скачиваем Maven отсюда: https://maven.apache.org/download.cgi

Нам понадобится zip архив с бинарными файлами. Ссылка на версию для Windows:
http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip

Распаковываем полученный архив. Я распаковал в C:\Program Files\Maven339

## Установка Git

Скачиваем и устанавливаем git: https://git-scm.com/download/

Подробное руководство по работе с git можно посмотреть здесь же: 
https://git-scm.com/book/ru/v2

В том числе по установке:
https://git-scm.com/book/ru/v2/Введение-Установка-Git

## Настройка переменных окружения

Добавляем новую переменную окружения:
JAVA_HOME=C:\Program Files\Java\jdk1.8.0_111

Добавляем пути до каталогов bin Maven и Git в перменную окружения PATH:
c:\Program Files\Git\bin\
c:\Program Files\Maven339\bin\

Открываем коммандную строку и проверяем что все правильно 
(здесь и далее символ > в начале строки обозначает приглашение командной строки, набирать его не нужно J):

>java -version

>java version "1.8.0_111"

>Java(TM) SE Runtime Environment (build 1.8.0_111-b14)

>Java HotSpot(TM) Client VM (build 25.111-b14, mixed mode, sharing)

> mvn -v

Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T23:41:47+07:00)

Maven home: c:\Program Files\Maven339\bin\..

Java version: 1.8.0_111, vendor: Oracle Corporation

Java home: C:\Program Files\Java\jdk1.8.0_111\jre

Default locale: ru_RU, platform encoding: Cp1251

OS name: "windows 7", version: "6.1", arch: "x86", family: "dos"


> git --version

git version 2.10.1.windows.1

Если все выглядит точно так же, то поздравляю, мы справились с установкой.

##Запуск проекта из командной строки:

Открываем командную строку и выполняем команды:
> git clone https://github.com/edu-xored/memorise

> cd memorise

> mvn install

> cd memorise-web-app

> mvn jetty:run

Когда увидим в терминале Started Jetty Server, открываем в браузере URL http://localhost:8080
Если все прошло успешно, то вы увидите форму логина.

##Импорт проекта в IDE

1. Запускаем IDE
2. Импортируем склонированный проект как Maven проект:
 - Выбираем на стартовом экране пункт Import Project
 - В появившемся окне находим каталог с проектом и нажимаем Ok
 - В окне Import Project выбираем пункт Import project from external model и выбираем Maven, затем нажимаем Next
 - На следующей странице можно отметить опцию Import Maven projects automatically
В процессе импорта проекта важно убедиться, что Idea определила установленную JDK, если нет, то помогаем.
3. Чтобы собрать проект, открываем окно Maven Projects (вкладка в правой части окна, либо через меню View > Tool Windows > Maven Projects), разворачиваем Mater Project > Lifecycle и жмем Install  

##Project technical details

## Components
### Platform 

Is base bootstrap project and common crosscuting (orm/). 

### Memorise Web application
* Frontend - HTML5 responsive UI using REST to communicate with backend 
* Backend
** REST Services
** Async processes using crawler to find already registered memes and cache some new memes
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
* crawler4j
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
* Provide integration test for new components like crawler 
* Provide description of some other cases and change you have made in therms of Quality Assurance Engineer (I've changed the logic of memes searching)

### Move issue card to the Completed column when it's closed

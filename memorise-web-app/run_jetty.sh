#!/bin/sh

export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=4006,server=y,suspend=n"
mvn jetty:run 


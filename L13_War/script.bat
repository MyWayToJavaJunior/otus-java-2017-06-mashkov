@echo off
start /wait "maven" mvn clean package
copy C:\Users\User\IdeaProjects\otus-java-2017-06-mashkov\L13_War\target\root.war C:\Users\User\Downloads\jetty-distribution-9.4.6.v20170531\jetty-distribution-9.4.6.v20170531\webapps
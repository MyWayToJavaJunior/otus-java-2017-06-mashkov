@echo off
call mvn clean package
copy "%~dp0\target\root.war" "%JETTY_HOME%\webapps"
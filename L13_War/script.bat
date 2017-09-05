@echo off
start /WAIT /B "" mvn clean package
copy C:\Users\Anton\Downloads\lodgon-openmapfx-e6051aa87826\otus-java-2017-06-mashkov\L13_War\target\root.war C:\Users\Anton\Downloads\jetty-distribution-9.4.6.v20170531\jetty-distribution-9.4.6.v20170531\webapps
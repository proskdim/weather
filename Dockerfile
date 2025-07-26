FROM tomcat:11.0

COPY ./source/weather_3-0.1.0-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

EXPOSE 8080
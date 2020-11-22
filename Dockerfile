FROM maven:3.6.1-jdk-8 as maven_builder
ADD . /app
WORKDIR /app
RUN ["mvn", "package"]

FROM tomcat:9.0-alpine
COPY --from=maven_builder /app/target/webapp.war $CATALINA_HOME/webapps
ADD dependencies/mysql-connector-java-8.0.22.jar $CATALINA_HOME/lib/
ADD dependencies/web.xml $CATALINA_HOME/conf/

EXPOSE 8080

CMD ["catalina.sh","run"]
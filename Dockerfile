FROM maven:3.6.1-jdk-8 as maven_builder
ADD . /app
WORKDIR /app
RUN ["mvn", "package"]

FROM tomcat:8.5.43-jdk8
COPY --from=maven_builder /app/target/webapp.war $CATALINA_HOME/webapps
EXPOSE 8080

CMD ["catalina.sh","run"]
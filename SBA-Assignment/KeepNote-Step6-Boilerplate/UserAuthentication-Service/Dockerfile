FROM java:8-jre
ENV MYSQL_HOST=localhost
ENV MYSQL_DATABASE=note_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
WORKDIR usr/src
ADD ./target/UserAuthentication-Service-0.0.1-SNAPSHOT.jar usr/src/UserAuthenticationService-0.0.1-SNAPSHOT.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","UserAuthenticationService-0.0.1-SNAPSHOT.jar"]
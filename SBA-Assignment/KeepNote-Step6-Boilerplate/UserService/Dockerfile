FROM java:8-jre
ENV MONGO_HOST=localhost
ENV MONGO_DATABASE=notedb
ENV MONGO_USERNAME=root
ENV MONGO_PASSWORD=root
ENV MONGO_PORT=27017
WORKDIR usr/src
ADD ./target/UserService-0.0.1-SNAPSHOT.jar usr/src/UserService-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","UserService-0.0.1-SNAPSHOT.jar"]
FROM java:8-jre
ENV MONGO_HOST=localhost
ENV MONGO_DATABASE=notedb
ENV MONGO_USERNAME=root
ENV MONGO_PASSWORD=root
ENV MONGO_PORT=27017
WORKDIR usr/src
ADD ./target/CategoryService-0.0.1-SNAPSHOT.jar usr/src/CategoryService-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","CategoryService-0.0.1-SNAPSHOT.jar"]
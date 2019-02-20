FROM java:8-jre
ENV MONGO_HOST=localhost
ENV MONGO_DATABASE=notedb
ENV MONGO_USERNAME=root
ENV MONGO_PASSWORD=root
ENV MONGO_PORT=27017
WORKDIR usr/src
ADD ./target/ReminderService-0.0.1-SNAPSHOT.jar usr/src/ReminderService-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","ReminderService-0.0.1-SNAPSHOT.jar"]
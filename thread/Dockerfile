FROM adoptopenjdk/openjdk11:ubi
MAINTAINER com.app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./
ENTRYPOINT ["java", "-jar", "-Dspring.cloud.bootstrap.enabled=true", "thread-0.0.1-SNAPSHOT.jar"]

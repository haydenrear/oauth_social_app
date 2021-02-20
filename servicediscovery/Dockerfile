FROM adoptopenjdk/openjdk11:ubi
MAINTAINER com.app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.cloud.bootstrap.enabled=true", "/app.jar"]
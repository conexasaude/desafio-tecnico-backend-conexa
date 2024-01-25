FROM openjdk:21-jdk-slim

VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
RUN bash -c 'touch /app.jar'
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]

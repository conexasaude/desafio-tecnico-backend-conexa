FROM maven:3.9.0-eclipse-temurin-17
COPY ./ /desafio/
WORKDIR /desafio
EXPOSE 8080
RUN ["mvn", "clean"]
RUN ["mvn", "package", "-Dmaven.test.skip=true"]
ENTRYPOINT ["java", "-jar", "./target/desafio-1.0.jar"]
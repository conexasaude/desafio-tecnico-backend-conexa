FROM maven:3.9.0-eclipse-temurin-17
COPY ./ /desafio/
WORKDIR /desafio/
EXPOSE 8080
RUN ["mvn", "clean"]
RUN ["mvn", "test"]
ENTRYPOINT ["mvn", "spring-boot:run"]
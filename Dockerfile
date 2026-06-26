FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /workspace/app

COPY . .

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target

COPY --from=build ${DEPENDENCY}/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

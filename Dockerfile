FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace/app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target

COPY --from=build ${DEPENDENCY}/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
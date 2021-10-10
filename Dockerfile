FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar

FROM openjdk:11.0.6-jre-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar  /app/gif-currency-service.jar
ENTRYPOINT ["java", "-jar","/app/gif-currency-service.jar"]

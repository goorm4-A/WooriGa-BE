FROM gradle:8.13-jdk17 AS builder
#Gradle 8.13
COPY . /usr/src
WORKDIR /usr/src
RUN gradle wrapper --gradle-version 8.13
RUN ./gradlew clean build -x test

FROM openjdk:17-jdk
#debian기반
COPY --from=builder /usr/src/build/libs/server-0.0.1-SNAPSHOT.jar /usr/app/app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]
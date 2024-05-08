FROM openjdk:17 AS builder
WORKDIR /backend
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM openjdk:17
COPY --from=builder /builder/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
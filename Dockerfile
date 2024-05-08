FROM openjdk:17 AS builder
WORKDIR /backend
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x . /gradlew
RUN ./gradlew bootjar

FROM openjdk:17
COPY --from=builder /backend/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
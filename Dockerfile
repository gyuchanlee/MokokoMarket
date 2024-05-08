FROM openjdk:17 AS builder
WORKDIR /backend
# Gradle 설치
ENV GRADLE_VERSION=7.4.2
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && rm gradle-${GRADLE_VERSION}-bin.zip

ENV PATH=/builder/gradle-${GRADLE_VERSION}/bin:$PATH

COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN gradle clean bootJar

FROM openjdk:17
COPY --from=builder /builder/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
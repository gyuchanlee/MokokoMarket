FROM openjdk:17 AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY gradle/wrapper gradle/wrapper
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# 런타임 스테이지
FROM openjdk:17
WORKDIR /app
# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar
# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
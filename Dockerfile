# 빌드 스테이지
FROM openjdk:17 AS builder

WORKDIR /app

# Gradle 래퍼 파일 복사
COPY gradlew .
COPY gradle gradle

# 프로젝트 파일 복사
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Gradle을 사용하여 프로젝트 빌드
RUN chmod +x gradlew
RUN ./gradlew bootJar

# 런타임 스테이지
FROM openjdk:17

WORKDIR /app

# 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
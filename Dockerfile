FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# 프로젝트 빌드
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# React 빌드
COPY frontend frontend
WORKDIR /app/frontend
RUN npm install
RUN npm run build

# 최종 이미지 생성
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/frontend/build frontend
ENTRYPOINT ["java", "-jar", "app.jar"]
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

# Node.js 설치
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
RUN apt-get update && apt-get install -y nodejs npm

# React 빌드
COPY /src/main/mokokofront mokokofront
WORKDIR /app/mokokofront
RUN npm install --force
RUN npm run build

# 최종 이미지 생성
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/mokokofront/build /static

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
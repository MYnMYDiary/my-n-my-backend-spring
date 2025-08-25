# JDK 17 베이스 이미지 사용
FROM eclipse-temurin:17-jdk AS build
# 작업 디렉토리 설정
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew && ./gradlew clean bootJar

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

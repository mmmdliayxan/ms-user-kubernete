# 1-ci mərhələ: build
FROM gradle:8.4.0-jdk17 AS builder

WORKDIR /app

COPY . .

# gradlew faylına icra icazəsi ver
RUN chmod +x ./gradlew

# Gradle ilə tətbiqi build et
RUN ./gradlew clean build -x test --no-daemon

# 2-ci mərhələ: run
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/ms-user-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

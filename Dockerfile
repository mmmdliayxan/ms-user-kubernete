# 1-ci mərhələ: Build
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Layihənin fayllarını kopyalayırıq
COPY . .

# Gradle build edir (burada wrapper icra olunur)
RUN ./gradlew clean build --no-daemon

# 2-ci mərhələ: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Sadəcə 1-ci mərhələdə yaradılmış jar faylını kopyalayırıq
COPY --from=builder /app/build/libs/ms-user-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
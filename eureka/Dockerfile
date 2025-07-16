# Этап сборки
FROM maven:3-eclipse-temurin-21-alpine AS build
WORKDIR /src

# Копируем pom.xml и загружаем зависимости (кешируя их)
COPY pom.xml /src/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -f /src/pom.xml dependency:go-offline

# Копируем весь исходный код и собираем проект
COPY . /src
RUN --mount=type=cache,target=/root/.m2 mvn -f /src/pom.xml clean package -DskipTests

# Этап запуска
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный JAR
COPY --from=build /src/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:-UseAdaptiveSizePolicy", "-Xmx5120m", "-Xms256m", "-Xss256k", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

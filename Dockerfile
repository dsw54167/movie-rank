# -------- Build stage --------
FROM gradle:8.14.3-jdk21 AS builder

WORKDIR /build
COPY . .

RUN gradle bootJar -x test


# -------- Run stage --------
FROM amazoncorretto:21-alpine

WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
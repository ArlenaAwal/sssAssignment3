# Build stage
FROM maven:3.9.9-eclipse-temurin-21 as builder
WORKDIR /build
COPY . /build
RUN mvn -q -DskipTests package

# Run stage
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app
COPY --from=builder /build/target/*.jar /app/app.jar
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
ENTRYPOINT ["java","-jar","/app/app.jar"]

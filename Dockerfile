FROM eclipse-temurin:21-jre-alpine

# The upstream image can lag behind Alpine security package rebuilds.
# Apply available fixes before the vulnerability scan runs in CI.
RUN apk upgrade --no-cache

WORKDIR /app
COPY --chmod=0444 app/build/libs/devpath-ai.jar app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

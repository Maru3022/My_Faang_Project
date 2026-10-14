FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --chmod=0444 app/build/libs/devpath-ai.jar app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

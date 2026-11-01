
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/cicd-demo-1.0.0.jar app.jar

ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD ["sh", "-c", "curl -fsS http://localhost:${SERVER_PORT}/actuator/health || exit 1"]

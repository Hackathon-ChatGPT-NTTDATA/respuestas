FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 80
ADD target/respuestas-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
# FROM amazoncorretto:21
# WORKDIR /app
# CMD [ "./mvnw", "spring-boot:run" ]
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve
RUN ./mvnw package -DskipTests
CMD ["java", "-jar", "target/airventure-back-0.0.1-SNAPSHOT.jar"]





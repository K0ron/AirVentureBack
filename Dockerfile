# FROM amazoncorretto:21
# WORKDIR /app
# CMD [ "./mvnw", "spring-boot:run" ]
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve
CMD ["./mvnw", "spring-boot:run"]





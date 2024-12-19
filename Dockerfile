# FROM amazoncorretto:21
# WORKDIR /app
# CMD [ "./mvnw", "spring-boot:run" ]

FROM eclipse-temurin:21-jdk-alpine  
WORKDIR /app
COPY . /app
RUN ./mvnw package
CMD ["java", "-XX:UseSVE=0", "-jar", "/app/target/your-app.jar"]

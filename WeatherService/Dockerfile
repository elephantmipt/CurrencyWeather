FROM openjdk:8-jdk-alpine
EXPOSE 8082
RUN mkdir weatherService
COPY target/weather-0.0.1-SNAPSHOT.jar /weatherService
WORKDIR /weatherService
CMD ["java", "-jar", "weather-0.0.1-SNAPSHOT.jar"]
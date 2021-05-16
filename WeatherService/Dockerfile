FROM openjdk:8-jdk-alpine
EXPOSE 8082
RUN mkdir currencyService
COPY target/currencyservice-0.0.1-SNAPSHOT.jar /WeatherService
WORKDIR /WeatherService
CMD ["java", "-jar", "weather-0.0.1-SNAPSHOT.jar"]
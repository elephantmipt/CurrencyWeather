FROM openjdk:8-jdk-alpine
EXPOSE 8083
RUN mkdir currencyService
COPY target/currencyservice-0.0.1-SNAPSHOT.jar /predict
WORKDIR /predict
CMD ["java", "-jar", "predict-0.0.1-SNAPSHOT.jar"]
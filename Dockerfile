FROM adoptopenjdk/openjdk11:latest
WORKDIR /opt/app
COPY target/card-1.0.0.jar card-1.0.0.jar
CMD ["java", "-jar", "card-1.0.0.jar"]
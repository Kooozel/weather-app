FROM eclipse-temurin:17-alpine

COPY build/libs/weather-app-0.0.1-SNAPSHOT.jar weather-app-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","weather-app-0.0.1-SNAPSHOT.jar"]
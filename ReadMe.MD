# Weather App

This is a simple weather application that provides API endpoints for managing weather data.

## Running the Application with Docker Compose

To run the application using Docker Compose, follow these steps:

1. Clone this repository to your local machine:
    ```bash
    git clone https://github.com/Kooozel/weather-app.git

2. Run Docker Compose to start the application, it will fetch container from Docker Hub:
    ```bash
    docker-compose up -d
    ```

3. Once the containers are up and running, you can access the API documentation at [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

## Running Locally

If you prefer to run Spring Boot separately, follow these steps:

1. Ensure MongoDB is running on your local machine.

2. Start your Spring Boot application.

3. You can now access the API documentation at [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

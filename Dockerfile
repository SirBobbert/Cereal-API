# Use OpenJDK 21 the base image for running the app
#FROM openjdk:21
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# Set the working directory where subsequent commands run and files are placed
WORKDIR /app

# Copy the built Spring Boot JAR from the host into the image at /app/app.jar
COPY target/Cereal-0.0.1-SNAPSHOT.jar /app/app.jar

# Copy the CSV into the image so the app can read it at a known path
COPY import/data.csv /app/data.csv

# Define an environment variable the app can read for the CSV path
ENV DATA_CSV_PATH=/app/data.csv

# Document that the application listens on port 8080 inside the container
EXPOSE 8080

# Set the default command to start the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]


# How to build and run the Docker image:
# mvn clean package
# docker build -t cereal-api:latest .
# docker run --rm -p 8080:8080 cereal-api:latest


# How to check what db table contains
# docker exec -it my_database mysql -u root -p1234
# USE cereal_api;
# SHOW TABLES;
# DESCRIBE product;
# SELECT * FROM product;

# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM product; DESCRIBE product; SELECT * FROM product";
# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM manufacturer; DESCRIBE manufacturer; SELECT * FROM manufacturer";
# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM user; DESCRIBE user; SELECT * FROM user";
# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM product; DESCRIBE product; SELECT * FROM product where ID = 1";

# docker compose down -v
# docker compose down -v
# docker logs my_database
# Step 1: Use an official OpenJDK base image from Docker Hub
FROM openjdk:21

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
COPY target/Cereal-0.0.1-SNAPSHOT.jar /app/app.jar

# Step 4: Set environment variables for the application
# Copy the CSV so the path exists in the container
COPY import/data.csv /app/data.csv
ENV DATA_CSV_PATH=/app/data.csv

# Step 5: Expose the port your application runs on
EXPOSE 8080

# Step 6: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]

# How to build and run the Docker image:
# mvn clean package
# docker build -t cereal-api:latest .
# docker run --rm -p 8080:8080 cereal-api:latest


# Check what db table contains

# docker exec -it my_database mysql -u root -p1234
# USE cereal_api;
# SHOW TABLES;
# DESCRIBE product;
# SELECT * FROM product;

# docker compose down -v
# docker compose down -v
# docker logs my_database

# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM product; DESCRIBE product; SELECT * FROM product";
# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM manufacturer; DESCRIBE manufacturer; SELECT * FROM manufacturer";
# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM user; DESCRIBE user; SELECT * FROM user";


# docker exec -it my_database mysql -u root -p1234 -e "USE cereal_api; SELECT COUNT(*) FROM product; DESCRIBE product; SELECT * FROM product where ID = 1";

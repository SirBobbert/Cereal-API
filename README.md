Opgave:
[Cereal API.pdf](../Cereal%20API.pdf)

Kravspecifikationer:
[Kravspec - Cereal API.pdf](../Kravspec%20-%20Cereal%20API.pdf)

# Cereal API

A simple RESTful API for managing a collection of cereals.

## Description

This is a simple implementation of a RESTful API for managing a collection of cereals.
The API allows authorized users to perform CRUD (Create, Read, Update, Delete) operations on cereal data stored in a SQL
database. It is required to be authorized before being able to perform CRUD-operations (except for GET operationer).
It is also possible to filter cereals based on various attributes such as name, manufacturer, and nutritional
information.

### Technologies

- Java
- Spring Boot
- Maven
- MySQL Workbench
- Postman

### Features

- CRUD operations for cereals
- Authenticated users can create, update, and delete cereals
- Unauthenticated users can only read cereal data
- Creation of user accounts
- Basic user authentication
- Error handling and validation
- Unit tests/controller slice tests
- Logging with SLF4J
- API documentation with Swagger
- Integration with MySQL database

### Future features

- Filtering cereals based on attributes
- Pagination for large datasets
- Advanced search functionality
- User roles and permissions

## Getting Started

### Dependencies

- Java 21
- Maven
- MySQL Workbench (full installation) or MySQL Server + MySQL Shell
- Postman (for testing the API)
- Git (for cloning the repository)
- IDE (e.g., IntelliJ IDEA, Eclipse)
- Swagger UI (for API documentation)

### Installing and Running

1. Clone the repository:
   ```bash
   git clone

2. Navigate to the project directory:
   ```bash
   cd cereal-api
   ```

3. Set up the MySQL database:
    - Open MySQL Workbench or MySQL Shell.
    - Create a new database named `cereal_db`:
      ```sql
      CREATE DATABASE cereal_db;
      ```

4. Configure the database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cereal_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

5. Build the project using Maven:
   ```bash
   mvn clean install
   ```

6. Run the application:
   ```bash
    mvn spring-boot:run
    ```

### Testing the API

1. Open Postman.
2. Use the following base URL for the API: `http://localhost:8080/api
3. Test the endpoints using Postman. You can refer to the Swagger UI for API documentation:
    - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
    - OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Help

If you encounter any issues or have questions, please refer to the documentation or open an issue in the repository.

## Authors

Robert Pallesen - [GitHub](github.com/SirBobbert)

## Version History

- 1.0.0
    - Initial release
    - Basic CRUD operations for cereals
    - User authentication
    - Error handling and validation
    - Unit tests
    - Controller slice tests
    - Logging with SLF4J
    - API documentation with Swagger
    - Integration with MySQL database
    - Filtering cereals based on attributes
    - See [commit history](https://github.com/SirBobbert/Cereal-API/commits/main/) for more details

## License

This project is licensed under the MIT License - see
the [LICENSE](https://github.com/SirBobbert/Cereal-API/blob/main/LICENSE) file for details.

## Acknowledgments

- Inspiration: [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- Spring Boot Security: [Spring Security](https://spring.io/projects/spring-security)
- Tutorials for TDD in Spring
  Boot: [Baeldung Spring Boot Tutorials](https://medium.com/@sharmapraveen91/mastering-test-driven-development-tdd-a-step-by-step-guide-to-building-a-product-crud-api-c46e0a5fedce)
- JUnit: [JUnit](https://junit.org/junit5/)
- Mockito: [Mockito](https://site.mockito.org/)


- Postman: [Postman](https://www.postman.com/)
- Swagger: [Swagger](https://swagger.io/)
- MySQL: [MySQL](https://www.mysql.com/)
- SLF4J: [SLF4J](http://www.slf4j.org/)
- Maven: [Maven](https://maven.apache.org/)


- BCrypt: [BCrypt](https://www.mindrot.org/projects/jBCrypt/)
- Lombok: [Lombok](https://projectlombok.org/)


- Thanks to my friends and classmates for their support and feedback during the development of this project.
- Thanks to the open-source community for providing valuable resources and libraries that made this project possible.
- Thanks to ChatGPT for assistance with code snippets and explanations.
- Thanks to GitHub Copilot for code suggestions and autocompletions.
- Thanks to my cats for keeping me company during long coding sessions :cat: :cat2:
- Thanks to my girlfriend for her patience and understanding while I worked on this project :heart:
- And finally, thanks to you for checking out this project! :smiley:
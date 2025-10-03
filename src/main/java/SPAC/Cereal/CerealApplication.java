package SPAC.Cereal;

import SPAC.Cereal.util.DBUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/*
    Main application class for the Cereal application.
    It bootstraps the Spring Boot application and conditionally seeds the database with initial data.
*/

@SpringBootApplication
@RequiredArgsConstructor
public class CerealApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerealApplication.class, args);
    }

    // Injects initial data into the database if the property 'app.seed.enabled' is set to true
    // Adds user and products to the database
    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
    CommandLineRunner injectDB(DBUpdater updater) {
        return args -> {
            updater.addAdminUserToDB();
            updater.addProductsToDB();
        };
    }
}

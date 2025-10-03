package SPAC.Cereal;

import SPAC.Cereal.util.DBUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class CerealApplication {

    // TODO: Readme + comments

    public static void main(String[] args) {
        SpringApplication.run(CerealApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
    CommandLineRunner injectDB(DBUpdater updater) {
        return args -> {
            updater.addAdminUserToDB();
            updater.addProductsToDB();
        };
    }
}

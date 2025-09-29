package SPAC.Cereal;

import SPAC.Cereal.util.DBUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class CerealApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerealApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(DBUpdater updater) {
        return args -> {
            updater.addToSql();
        };
    }
}

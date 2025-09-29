package SPAC.Cereal;

import SPAC.Cereal.service.DBUpdater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CerealApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerealApplication.class, args);

        // path to csv
        // "C:\Specialisterne - Opgaver\Uge 3\data\Cereal.csv"

        DBUpdater.addToSql();


    }
}

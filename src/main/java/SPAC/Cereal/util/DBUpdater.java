package SPAC.Cereal.util;

import SPAC.Cereal.model.CerealType;
import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class DBUpdater {

    private final ProductRepository productRepository;

    @Value("${data.csv.path}")
    private String csvPath;

    @Transactional
    public void addToSql() {

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;

            // skip first two lines (heading and datatype lines)
            br.readLine();
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] data = line.split(";");

                // if data length is less than 16, skip this line
                if (data.length < 16) continue;

                Product p = Product.builder()
                        .name(data[0])
                        .mfr(Manufacturer.valueOf(data[1]))
                        .type(CerealType.valueOf(data[2]))
                        .calories(Integer.parseInt(data[3]))
                        .protein(Integer.parseInt(data[4]))
                        .fat(Integer.parseInt(data[5]))
                        .sodium(Integer.parseInt(data[6]))
                        .sugars(Float.parseFloat(data[7]))
                        .potass(Float.parseFloat(data[8]))
                        .vitamins(Integer.parseInt(data[9]))
                        .shelf(Integer.parseInt(data[10]))
                        .fiber(Float.parseFloat(data[11]))
                        .carbo(Float.parseFloat(data[12]))
                        .weight(Float.parseFloat(data[13]))
                        .cups(Float.parseFloat(data[14]))
                        // helper class to parse this due to it having multiple points
                        .rating(parseFloatWithMultiplePoints(data[15]))
                        .build();

                System.out.println("Inserting: " + p);
                productRepository.save(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Data import completed.");
    }

    private static Float parseFloatWithMultiplePoints(String s) {
        s = s.trim().replace(',', '.');
        int firstDot = s.indexOf('.');
        if (firstDot != -1) {
            s = s.substring(0, firstDot + 1) + s.substring(firstDot + 1).replace(".", "");
        }
        return Float.valueOf(s);
    }
}
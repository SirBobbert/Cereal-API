package SPAC.Cereal.util;

import SPAC.Cereal.model.CerealType;
import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.model.User;
import SPAC.Cereal.repository.ProductRepository;
import SPAC.Cereal.repository.UserRepository;
import SPAC.Cereal.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class DBUpdater {

    private final ProductRepository productRepository;
    private final UserService userService;

    @Value("${data.csv.path}")
    private String csvPath;

    int lineNo, inserted = 0;
    String line;

    @Transactional
    public void addAdminUserToDB() {

        User user = User.builder()
                .name("admin")
                .password("1234")
                .build();

        userService.createUser(user);
    }

    @Transactional
    public void addProductsToDB() {

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

            // skip header lines
            br.readLine();
            br.readLine();

            // read each line
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;

                try {
                    // split line into fields by semicolon
                    String[] data = line.split(";");

                    // validate and normalize fields
                    String[] v = validateAndNormalize(data);

                    // create Product object
                    Product p = Product.builder()
                            .name(v[0])
                            .mfr(Manufacturer.valueOf(v[1]))
                            .type(CerealType.valueOf(v[2]))
                            .calories(Integer.parseInt(v[3]))
                            .protein(Integer.parseInt(v[4]))
                            .fat(Integer.parseInt(v[5]))
                            .sodium(Integer.parseInt(v[6]))
                            .fiber(Float.parseFloat(v[7]))
                            .carbo(Float.parseFloat(v[8]))
                            .sugars(Float.parseFloat(v[9]))
                            .potass(Float.parseFloat(v[10]))
                            .vitamins(Integer.parseInt(v[11]))
                            .shelf(Integer.parseInt(v[12]))
                            .weight(Float.parseFloat(v[13]))
                            .cups(Float.parseFloat(v[14]))
                            .rating(Float.parseFloat(v[15]))
                            .build();

                    // save to database
                    productRepository.save(p);
                    inserted++;

                } catch (Exception ex) {
                    System.err.println("================================================");
                    System.err.println("Skipping line " + lineNo);
                    System.err.println("Reason: " + ex.getMessage());
                    System.err.println("Line: " + line);
                    System.err.println("================================================");
                }
            }
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
        System.out.println("================================================");
        System.out.println("Inserted " + inserted + " records into the database.");
        System.out.println("================================================");
    }

    // iterates through each line, validates and normalizes them
    // normalization: trims whitespace, replaces commas with dots in floats, removes extra dots in rating
    // validation: checks for null/empty, correct enum values, correct number formats
    // returns normalized array or throws IllegalArgumentException
    public static String[] validateAndNormalize(String[] data) {

        // check for null or incorrect length
        if (data == null || data.length != 16)
            throw new IllegalArgumentException("Expected 16 fields, got " + (data == null ? 0 : data.length));

        // trim whitespace and check for empty fields
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].trim().isEmpty())
                throw new IllegalArgumentException("Field " + (i + 1) + " is null or empty");
            data[i] = data[i].trim();
        }

        // validate enums
        if (!data[1].matches("[AGKNPQR]"))
            throw new IllegalArgumentException("Invalid manufacturer code: " + data[1]);

        if (!data[2].matches("[HC]"))
            throw new IllegalArgumentException("Invalid cereal type code: " + data[2]);

        // validate integers and floats
        int[] intIdx = {3, 4, 5, 6, 10, 11, 12};
        for (int idx : intIdx) {
            try {
                Integer.parseInt(data[idx]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Field " + (idx + 1) + " must be an integer: " + data[idx]);
            }
        }

        // validate and normalize floats (replace commas with dots)
        int[] floatIdx = {7, 8, 9, 13, 14};
        for (int idx : floatIdx) {
            String norm = data[idx].replace(',', '.');
            try {
                Float.parseFloat(norm);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Field " + (idx + 1) + " must be a float: " + data[idx]);
            }
            data[idx] = norm;
        }

        // special handling for rating (field 16)
        String r = data[15].replace(',', '.').trim();
        int firstDot = r.indexOf('.');
        if (firstDot >= 0) {
            r = r.substring(0, firstDot + 1) + r.substring(firstDot + 1).replace(".", "");
        }
        try {
            Float.parseFloat(r);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field 16 must be a float: " + data[15]);
        }
        data[15] = r;

        // return normalized data
        return data;
    }
}
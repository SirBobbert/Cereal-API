package SPAC.Cereal.service;

import java.io.*;
import java.util.Arrays;

public class DBUpdater {

    // Opret en database til at lagre cereal.csv.
    // Lav en parser, der kan indlæse produktdata fra CSV-filen og indsætte dem i databasen.
    // Sørg for, at parseren kan håndtere ekstra data, hvis flere produkter tilføjes senere.
    // Sørg for, at databasen understøtter CRUD-operationer.
    // Sikre dataintegritet med relevante constraints (f.eks. unikke felter, ikke-null værdier).

    public static void addToSql() {

        File path = new File("C:\\Specialisterne - Opgaver\\Uge 3\\Cereal\\src\\main\\java\\SPAC\\Cereal\\service\\data.csv");

        String line;
        String delimiter = ";";
        boolean firstLine = true; // set to true if your CSV has a header row

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {
                // Skip blank lines
                if (line.isBlank()) continue;

                // Split keeping trailing empty fields
                String[] raw = line.split(delimiter, -1);

                // If there aren't at least 3 fields, we can't drop two safely
                if (raw.length < 3) continue;

                if (firstLine) {
                    firstLine = false;
                    // If the CSV has a header row, skip it entirely:
                    // Example: if (raw[0].equalsIgnoreCase("id")) continue;
                    // For now we just continue to parse; comment the next line if you do NOT want to skip header.
                    // continue;
                }

                // ---- Skip the first 2 entries/columns ----
                String[] data = Arrays.copyOfRange(raw, 2, raw.length);

                // Debug print: show fields after skipping first 2
                for (int i = 0; i < data.length; i++) {
                    System.out.println("col[" + i + "] = " + data[i]);
                }

                // ===== Example mapping (after skipping 2 columns) =====
                // Adjust this mapping to your actual CSV schema.
                // Typical cereal schema after trimming might be:
                // 0:name, 1:mfr, 2:type, 3:calories, 4:protein, 5:fat, 6:sodium,
                // 7:sugars, 8:potass, 9:vitamins, 10:shelf, 11:fiber, 12:carbo,
                // 13:weight, 14:cups, 15:rating

                Product p = Product.builder()
                        .name(data[0])
                        .mfr(null)                 // or map to enum if you have one
                        .type(null)                // or map to enum if you have one
                        .calories(parseIntSafe(data[3]))
                        .protein(parseIntSafe(data[4]))
                        .fat(parseIntSafe(data[5]))
                        .sodium(parseIntSafe(data[6]))
                        .sugars(parseIntSafe(data[7]))
                        .potass(parseIntSafe(data[8]))
                        .vitamins(parseIntSafe(data[9]))
                        .shelf(parseIntSafe(data[10]))
                        .fiber(parseFloatSafe(data[11]))
                        .carbo(parseFloatSafe(data[12]))
                        .weight(parseFloatSafe(data[13]))
                        .cups(parseFloatSafe(data[14]))
                        .rating(parseFloatSafe(data[15]))
                        .build();
//
//                // Insert into DB here (DAO / repository)
//                productRepository.save(p);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found: " + path, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV: " + path, e);
        }
    }

    // ---- Helpers to parse safely ----
    private static Integer parseIntSafe(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Float parseFloatSafe(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Float.parseFloat(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

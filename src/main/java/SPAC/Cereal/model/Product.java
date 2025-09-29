package SPAC.Cereal.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Manufacturer mfr;
    @Enumerated(EnumType.STRING)
    private CerealType type;

    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private float fiber;
    private float carbo;
    private float sugars;
    private float potass;
    private int vitamins;
    private int shelf;
    private float weight;
    private float cups;
    private float rating;
}

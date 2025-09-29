package SPAC.Cereal.service;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    // Ints
    private float calories;
    private float protein;
    private float fat;
    private float sodium;
    private float sugars;
    private float potass;
    private float vitamins;
    private float shelf;

    // Floats
    private float fiber;
    private float carbo;
    private float weight;
    private float cups;
    private float rating;
}

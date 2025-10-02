package SPAC.Cereal.repository;

import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByFat(int fat);

    List<Product> findByMfr(Manufacturer mfr);
}
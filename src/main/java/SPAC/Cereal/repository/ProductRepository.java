package SPAC.Cereal.repository;

import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    Repository interface for Product entity.
    Provides methods to perform CRUD operations and custom queries.
*/

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find products by their exact fat content
    List<Product> findByFat(int fat);

    // Find products by their manufacturer
    List<Product> findByMfr(Manufacturer mfr);
}
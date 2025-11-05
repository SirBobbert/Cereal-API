package SPAC.Cereal.service;

import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/*
    ProductService provides business logic for managing products.
    It interacts with the ProductRepository to perform CRUD operations,
    filtering, and retrieving product images.
*/

@Service
public class ProductService {

    // Repository for product data access
    private final ProductRepository repository;

    // Constructor-based dependency injection
    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // CRUD Operations

    // Retrieve all products
    public List<Product> getAll() {
        return repository.findAll();
    }

    // Retrieve a product by its ID
    public Optional<Product> getById(int id) {
        return repository.findById(id);
    }

    // Create a new product
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    // Return Optional.empty instead of throwing when id is missing
    public Optional<Product> updateProduct(int id, Product productToUpdate) {
        return repository.findById(id).map(product -> {
            // Set all mutable fields
            product.setName(productToUpdate.getName());
            product.setMfr(productToUpdate.getMfr());
            product.setType(productToUpdate.getType());
            product.setCalories(productToUpdate.getCalories());
            product.setProtein(productToUpdate.getProtein());
            product.setFat(productToUpdate.getFat());
            product.setSodium(productToUpdate.getSodium());
            product.setFiber(productToUpdate.getFiber());
            product.setCarbo(productToUpdate.getCarbo());
            product.setSugars(productToUpdate.getSugars());
            product.setPotass(productToUpdate.getPotass());
            product.setVitamins(productToUpdate.getVitamins());
            product.setShelf(productToUpdate.getShelf());
            product.setWeight(productToUpdate.getWeight());
            product.setCups(productToUpdate.getCups());
            product.setRating(productToUpdate.getRating());
            return repository.save(product);
        });
    }

    // Delete a product by its ID. Returns the deleted entity or empty if not found.
    @Transactional
    public Optional<Product> deleteProduct(int id) {
        return repository.findById(id).map(p -> {
            repository.delete(p);
            return p;
        });
    }

    // Filters

    // Find products by exact fat content
    public List<Product> findByFat(int value) {
        return repository.findByFat(value);
    }

    // Parse manufacturer in a case-insensitive way, return empty list on bad value
    public List<Product> findByMfr(String value) {
        try {
            Manufacturer m = Manufacturer.valueOf(value.toUpperCase(Locale.ROOT));
            return repository.findByMfr(m);
        } catch (IllegalArgumentException ex) {
            return List.of();
        }
    }

    // Retrieve product image by product ID
    public Resource getImageResourceById(int id) {
        // Note: name-based lookup is brittle when names contain symbols
        Product product = getById(id).orElseThrow();
        String productName = product.getName();

        ClassPathResource img = new ClassPathResource("static/images/" + productName + ".jpg");
        if (!img.exists()) img = new ClassPathResource("static/images/no-image.jpg");
        return img;
    }
}

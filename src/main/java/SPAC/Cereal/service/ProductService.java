package SPAC.Cereal.service;

import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // Update an existing product by its ID
    public Optional<Product> updateProduct(int id, Product productToUpdate) {
        Product product = repository.findById(id).orElseThrow();

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

        return Optional.of(repository.save(product));
    }

    // Delete a product by its ID
    public Product deleteProduct(int id) {
        Product product = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return product;
    }


    // Filters

    // Find products by exact fat content
    public List<Product> findByFat(int value) {
        return repository.findByFat(value);
    }

    // Find products by manufacturer
    public List<Product> findByMfr(String value) {
        Manufacturer m = Manufacturer.valueOf(value);
        return repository.findByMfr(m);
    }

    // Retrieve product image by product ID
    public Resource getImageResourceById(int id) {

        // TODO:
        // Should probably use mfr instead of name due to when creating a new product
        // the name might not match an existing image file.

        Product product = getById(id).orElseThrow();
        String productName = product.getName();

        ClassPathResource img = new ClassPathResource("static/images/" + productName + ".jpg");
        if (!img.exists()) img = new ClassPathResource("static/images/no-image.jpg");

        return img;
    }
}

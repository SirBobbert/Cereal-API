package SPAC.Cereal.service;

import SPAC.Cereal.model.Product;
import SPAC.Cereal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getOne(int id) {
        return repository.findById(id);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

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

    public Optional<Product> deleteProduct(int id) {
        Product product = repository.findById(id).orElseThrow();

        System.out.println("Product to be deleted: " + product);

        repository.deleteById(id);

        System.out.println("Product deleted.");
        return Optional.of(product);
    }
}

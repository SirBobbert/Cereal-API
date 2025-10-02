package SPAC.Cereal.controller;

import SPAC.Cereal.model.Product;
import SPAC.Cereal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Optional<Product>> createProduct(@RequestBody Product product) {
        Optional<Product> result = Optional.ofNullable(service.createProduct(product));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable int id) {
        Optional<Product> product = service.getById(id);
        return ResponseEntity.ok(product);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.getAll();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Product>> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> updatedProduct = service.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter/fat/{value}")
    public ResponseEntity<List<Product>> filterProductsByFat(@PathVariable int value) {
        List<Product> products = service.findByFat(value);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter/mfr/{mfr}")
    public ResponseEntity<List<Product>> filterProductsByManufacturer(@PathVariable String mfr) {
        List<Product> products = service.findByMfr(mfr);
        return ResponseEntity.ok(products);
    }
}

package SPAC.Cereal.controller;

import SPAC.Cereal.model.Product;
import SPAC.Cereal.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/*
    ProductController handles HTTP requests for managing products.
    It provides endpoints for creating, retrieving, updating, deleting,
    and filtering products, as well as serving product images.
*/

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Service layer for product operations
    private final ProductService service;

    // Constructor-based dependency injection
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product p) {
        Product saved = service.createProduct(p);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // Retrieve a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retrieve all products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Update an existing product by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody @Valid Product payload) {
        return service.updateProduct(id, payload)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Use deleteProduct's Optional to decide status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteProduct(id).isPresent()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // Filter products by exact fat value
    @GetMapping("/filter/fat/{value}")
    public ResponseEntity<List<Product>> filterByFat(@PathVariable int value) {
        return ResponseEntity.ok(service.findByFat(value));
    }

    // Filter products by manufacturer (mfr)
    @GetMapping("/filter/mfr/{mfr}")
    public ResponseEntity<List<Product>> filterByMfr(@PathVariable String mfr) {
        return ResponseEntity.ok(service.findByMfr(mfr));
    }

    // Get product image by product ID
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImageById(@PathVariable int id) {
        Resource img = service.getImageResourceById(id);

        // Log and return 404 if missing or unreadable
        if (!img.exists() || !img.isReadable()) {
            log.warn("Image not found or not readable for product id: {}", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
    }
}

package SPAC.Cereal.controller;

import SPAC.Cereal.model.Product;
import SPAC.Cereal.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product p) {
        Product saved = service.createProduct(p);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody @Valid Product payload) {
        return service.updateProduct(id, payload)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        if (service.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        return null;
    }

    @GetMapping("/filter/fat/{value}")
    public ResponseEntity<List<Product>> filterByFat(@PathVariable int value) {
        return ResponseEntity.ok(service.findByFat(value));
    }

    @GetMapping("/filter/mfr/{mfr}")
    public ResponseEntity<List<Product>> filterByMfr(@PathVariable String mfr) {
        return ResponseEntity.ok(service.findByMfr(mfr));
    }
}

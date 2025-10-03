package SPAC.Cereal.controller;

import SPAC.Cereal.model.Product;
import SPAC.Cereal.service.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service, ServletContext servletContext) {
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


    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImageById(@PathVariable int id) {
        Resource img = service.getImageResourceById(id);
        if (!img.exists() || !img.isReadable()) {
            log.warn("Image not found or not readable for product id: {}", id);
            return ResponseEntity.notFound().build();
        } else {

            log.info("Image found for product id: {}", id);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(img);

        }
    }

}
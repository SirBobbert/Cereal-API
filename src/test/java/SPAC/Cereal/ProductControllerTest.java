package SPAC.Cereal;

import SPAC.Cereal.controller.ProductController;
import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.model.User;
import SPAC.Cereal.service.ProductService;
import SPAC.Cereal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web layer test of ProductController with mocked ProductService.
 */

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for testing
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static Product p(int id, String name, int fat) {
        return Product.builder().id(id).name(name).fat(fat).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Given: a request object and a stubbed service response
        Product req = Product.builder().name("Name").fat(100).build();

        when(productService.createProduct(any(Product.class)))
                .thenAnswer(inv -> Product.builder()
                        .id(1) // simulate DB-generated id
                        .name(inv.getArgument(0, Product.class).getName())
                        .fat(inv.getArgument(0, Product.class).getFat())
                        .build());

        // When: performing a POST request to /api/products
        // Then: response should be OK with correct fields
        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Given: a stubbed product for id 1
        when(productService.getById(1)).thenReturn(Optional.of(p(1, "Name", 100)));

        // When: performing a GET request to /api/products/1
        // Then: response should be OK with expected product data
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Given: two stubbed products
        when(productService.getAll()).thenReturn(List.of(
                p(1, "Name1", 100),
                p(2, "Name2", 200)
        ));

        // When: performing a GET request to /api/products/all
        // Then: response should be OK with a list of 2 products
        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Name1"))
                .andExpect(jsonPath("$.[1].name").value("Name2"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateProductById() throws Exception {
        // Given: an updated product stub
        Product updated = p(1, "UpdatedName", 150);
        when(productService.updateProduct(1, updated)).thenReturn(Optional.of(updated));

        // When: performing a PUT request with updated body
        // Then: response should be OK with updated fields
        mockMvc.perform(put("/api/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.fat").value(150));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        // TODO: Add auth
        // Given: a stubbed delete response for id 1
        int id = 1;
        when(productService.deleteProduct(id)).thenReturn(p(id, "Deleted", 0));

        // When: performing a DELETE request
        // Then: response should be 204 No Content
        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFilterForParameter() throws Exception {

        List<Product> original = List.of(
                Product.builder().fat(100).build(),
                Product.builder().fat(100).build()
        );

        when(productService.findByFat(100)).thenReturn(original);

        // When: performing a GET request to /api/products/1
        // Then: response should be OK with expected product data
        mockMvc.perform(get("/api/products/filter/fat/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].fat").value(100));
    }

    @Test
    public void testFilterForMfr() throws Exception {

        List<Product> original = List.of(
                Product.builder().name("name1").fat(100).mfr(Manufacturer.N).build(),
                Product.builder().name("name2").fat(100).mfr(Manufacturer.N).build()
        );

        when(productService.findByMfr("N")).thenReturn(original);


        mockMvc.perform(get("/api/products/filter/mfr/N"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].mfr").value("N"));
    }

    @Test
    public void getProductImageById() throws Exception {

        Product product = Product.builder().id(1).name("100% Bran").build();

        when(productService.getImageResourceById(product.getId())).thenReturn(
                new org.springframework.core.io.ClassPathResource("static/images/" + product.getName() + ".jpg")
        );


        mockMvc.perform(get("/api/products/image/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }


}

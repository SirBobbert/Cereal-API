package SPAC.Cereal;

import SPAC.Cereal.controller.ProductController;
import SPAC.Cereal.model.Manufacturer;
import SPAC.Cereal.model.Product;
import SPAC.Cereal.service.ProductService;
import SPAC.Cereal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web layer test of ProductController with mocked ProductService.
 */

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // Build minimal product instances for tests
    private static Product p(int id, String name, int fat) {
        return Product.builder().id(id).name(name).fat(fat).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Given a request object and a stubbed service response
        Product req = Product.builder().name("Name").fat(100).build();

        when(productService.createProduct(any(Product.class)))
                .thenAnswer(inv -> Product.builder()
                        .id(1)
                        .name(inv.getArgument(0, Product.class).getName())
                        .fat(inv.getArgument(0, Product.class).getFat())
                        .build());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getById(1)).thenReturn(Optional.of(p(1, "Name", 100)));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAll()).thenReturn(List.of(
                p(1, "Name1", 100),
                p(2, "Name2", 200)
        ));

        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Name1"))
                .andExpect(jsonPath("$.[1].name").value("Name2"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateProductById() throws Exception {
        Product updated = p(1, "UpdatedName", 150);
        when(productService.updateProduct(1, updated)).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.fat").value(150));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        int id = 1;

        // Return present Optional to indicate a successful delete
        when(productService.deleteProduct(id)).thenReturn(Optional.of(p(id, "Deleted", 0)));

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFilterForParameter() throws Exception {
        List<Product> original = List.of(
                Product.builder().fat(100).build(),
                Product.builder().fat(100).build()
        );

        when(productService.findByFat(100)).thenReturn(original);

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
        // Use an in-memory resource to avoid depending on classpath files
        when(productService.getImageResourceById(1))
                .thenReturn(new ByteArrayResource(new byte[]{1, 2, 3}) {
                    @Override public String getDescription() { return "jpeg-bytes"; }
                });

        mockMvc.perform(get("/api/products/image/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }
}

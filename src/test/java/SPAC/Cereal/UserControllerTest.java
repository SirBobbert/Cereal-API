package SPAC.Cereal;

import SPAC.Cereal.controller.UserController;
import SPAC.Cereal.model.User;
import SPAC.Cereal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @WebMvcTest loads only the web layer for UserController.
 * UserService is mocked. No DB, no real logic.
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {

    /**
     * Mock HTTP client for controller endpoints.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mockito mock injected into the controller.
     */
    @MockitoBean
    private UserService userService;

    /**
     * JSON serializer for request bodies.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Helper to build users concisely.
     */
    private static User u(Integer id, String name, String password) {
        return User.builder().id(id).name(name).password(password).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Given: a signup payload and a stubbed service that returns the created user
        User request = u(1, "Bob", "1234");
        when(userService.createUser(any(User.class))).thenReturn(u(1, "Bob", "1234"));

        // When: posting to /api/users/create
        // Then: 201 Created with the created user data (note: exposing password is unsafe)
        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    @Test
    public void testLoginAsUser() throws Exception {
        // TODO: Add auth
        // Given: an existing user and a stub that finds by name
        User existing = u(1, "Robert", "1234");
        when(userService.findByName("Robert")).thenReturn(existing);

        // When: posting credentials to /api/users/login
        // Then: 200 OK with user data (again, avoid returning password in real APIs)
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Robert"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    // TODO: Add logout test once token/session handling exists.
}

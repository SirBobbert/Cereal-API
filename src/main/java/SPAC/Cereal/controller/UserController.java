package SPAC.Cereal.controller;

import SPAC.Cereal.model.User;
import SPAC.Cereal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    UserController handles HTTP requests for managing users.
    It provides an endpoint for creating new users.
*/

@RestController
@RequestMapping("api/users")
public class UserController {

    // Service layer for user operations
    private final UserService service;

    // Constructor-based dependency injection
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // Create a new user
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }
}

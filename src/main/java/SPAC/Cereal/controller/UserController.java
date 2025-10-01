package SPAC.Cereal.controller;

import SPAC.Cereal.model.User;
import SPAC.Cereal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User result = service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User result = service.findByName(user.getName());

        if (result == null || !result.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = UUID.randomUUID().toString();

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(result);

    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser(@RequestBody User user) {
        return ResponseEntity.ok().build();
    }
}

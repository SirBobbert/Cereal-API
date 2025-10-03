package SPAC.Cereal.service;

import SPAC.Cereal.model.User;
import SPAC.Cereal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
    UserService provides business logic for managing users.
    It interacts with the UserRepository to perform CRUD operations
    and handle user authentication.
*/

@Service
public class UserService {

    // Repository for user data access
    private final UserRepository repository;

    // Password encoder for secure password storage
    private final PasswordEncoder encoder;

    // Constructor-based dependency injection
    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // Create a new user with encoded password
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    // Find a user by their name for login
    public User findByName(String name) {
        return repository.findByName(name).orElse(null);
    }
}

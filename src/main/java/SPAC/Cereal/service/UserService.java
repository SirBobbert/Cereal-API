package SPAC.Cereal.service;

import SPAC.Cereal.model.User;
import SPAC.Cereal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User findByName(String email) {
        return repository.findByName(email).orElse(null);
    }
}

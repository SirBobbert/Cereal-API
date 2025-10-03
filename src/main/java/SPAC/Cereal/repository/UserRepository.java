package SPAC.Cereal.repository;

import SPAC.Cereal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    Repository interface for User entity.
    Provides methods to perform CRUD operations and custom queries.
*/

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find a user by their name to login
    Optional<User> findByName(String name);
}

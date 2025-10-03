package SPAC.Cereal.config;

import SPAC.Cereal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/*
    Service to load user-specific data for authentication.
    Implements UserDetailsService to integrate with Spring Security.
*/

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    // Repository for user data access
    private final UserRepository users;

    // Load user details by username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find user by username or throw exception if not found
        var u = users.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        System.out.println("Auth lookup for " + username);

        // Build and return UserDetails object with username, password, and roles if found
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getName())
                .password(u.getPassword()) // has to be bcrypt-hashed
                .roles("ADMIN")
                .build();
    }
}


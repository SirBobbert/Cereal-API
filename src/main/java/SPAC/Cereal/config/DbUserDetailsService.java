package SPAC.Cereal.config;

import SPAC.Cereal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = users.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));


        System.out.println("Auth lookup for " + username);


        // fast rolle "ADMIN" er nok til at autentificere
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getName())
                .password(u.getPassword()) // skal være BCrypt-hash
                .roles("ADMIN")
                .build();
    }
}


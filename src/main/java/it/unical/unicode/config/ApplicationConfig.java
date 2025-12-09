package it.unical.unicode.config;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class ApplicationConfig {

    private final UserDAO userDAO;

    public ApplicationConfig(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userDAO.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword_hash(),
                    Collections.emptyList()
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
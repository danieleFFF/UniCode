package it.unical.unicode.config;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailService implements UserDetailsService {

    private final UserDAO userDAO;

    public MyUserDetailService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword_hash(),
                Collections.emptyList()
        );
    }
}
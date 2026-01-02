package it.unical.unicode.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                ).cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Autenticazione
                        .requestMatchers("/api/login", "/api/logout").permitAll()
                        .requestMatchers("/api/users/register").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // Lettura esercizi, avatar e argomenti teoria (pubblici)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/exercises").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/exercises/{id}").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/exercises/{id}/tests")
                        .permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/avatars").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/users/leaderboard").permitAll()
                        .requestMatchers("/api/topics/**").permitAll()

                        // === ENDPOINT PROTETTI (richiedono autenticazione) ===
                        .requestMatchers("/api/users/profile").authenticated()
                        .requestMatchers("/api/users/password").authenticated()
                        .requestMatchers("/api/users/avatar").authenticated()
                        .requestMatchers("/api/users/delete").authenticated()
                        .requestMatchers("/api/submissions/**").authenticated()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/exercises/{id}/run")
                        .authenticated()

                        // Tutto il resto richiede autenticazione
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter()
                                    .write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\"}");
                        })


                ).formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .successHandler((request, response, authentication) -> {
                            // Successo: restituisce 200 OK
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\": \"Login successful\"}");                        })
                        .failureHandler((request, response, exception) -> {
                            // Fallimento: restituisce 401 UNAUTHORIZED
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        // In caso di successo del logout, restituisce 200
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\": \"Logout successful\"}");                        })
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
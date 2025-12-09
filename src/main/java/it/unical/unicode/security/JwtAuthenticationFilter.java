package it.unical.unicode.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Controllo se c'è il Token nell'Header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Estraggo il Token (tolgo "Bearer " iniziale)
        jwt = authHeader.substring(7);

        // 3. Estraggo l'email dal Token
        // Se il token è manomesso o scaduto, qui potrebbe lanciare eccezione,
        // ma Spring la gestirà restituendo 403.
        userEmail = jwtService.extractUsername(jwt);

        // 4. Se ho trovato l'email e l'utente non è già autenticato...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carico i dettagli dell'utente dal DB (usando il Bean che abbiamo fatto in SecurityConfig!)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. Valido il token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Creiamo il "Pass" per Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 6. Diciamo a Spring: "Ok, questo utente è autenticato"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
package it.unical.unicode.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor // Crea il costruttore per i campi final in automatico
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Serve per caricare i dati dal DB

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        //  Cerchiamo l'header "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //  Se non c'è l'header o non inizia con "Bearer ", lasciamo perdere e passiamo oltre
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //  Estraiamo il token (togliamo "Bearer " che sono 7 caratteri)
        jwt = authHeader.substring(7);

        //  Estraiamo la mail/username dal token usando il  Service
        userEmail = jwtService.extractUsername(jwt);

        // Se abbiamo trovato la mail E l'utente non è ancora autenticato nel contesto
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carichiamo i dettagli dell'utente dal DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Usiamo il  metodo isTokenValid per controllare se è tutto ok
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Creiamo l'oggetto di autenticazione
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //  Diciamo a Spring "Questo utente è loggato!"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
package com.example.hardstyle_hub_backend.security.jwt;

import com.example.hardstyle_hub_backend.service.security.CustomUserDetailsService; // Tu UserDetailsService
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Obtiene JWT del request HTTP
        String token = getTokenFromRequest(request);

        // Valida token y obtener nombre de usuario
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);

            // Carga usuario del servicio
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Crea objeto de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Contraseña no es necesaria después de la validación del token
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establece la autenticación en el SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // ontinua con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extraer el JWT de la cabecera "Authorization"
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); // Elimina "Bearer " del principio
        }
        return null;
    }
}
package com.example.SpringBootProject.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Clave secreta para firmar el token 
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    // Tiempo de expiración del token en milisegundos (1 hora = 3600000)
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // Generar JWT
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    // Obtener la clave de firma
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Obtener el nombre de usuario del JWT
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    // Validar el JWT
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            // Token JWT mal formado o inválido
            System.err.println("Token JWT inválido: " + e.getMessage());
        } catch (ExpiredJwtException e){
            // Token JWT expirado
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e){
            // Token JWT no soportado
            System.err.println("Token JWT no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e){
            // Cadena de claims JWT vacía
            System.err.println("Cadena de claims JWT vacía: " + e.getMessage());
        }
        return false;
    }
}
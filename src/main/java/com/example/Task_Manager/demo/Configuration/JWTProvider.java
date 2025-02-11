package com.example.Task_Manager.demo.Configuration;

import com.example.Task_Manager.demo.Model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
       List<String> roleList = auth.getAuthorities().stream()
               .map(GrantedAuthority::getAuthority)
               .collect(Collectors.toList());

        if (roleList.isEmpty()) {
            throw new IllegalStateException("Role not found in the authentication object");
        }

        String jwt = Jwts.builder()
                .setIssuer("Kulbirr")
                .claim("email", auth.getName()) // Adding user email
                .claim("roles", roleList)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWTConstant.EXPIRATION_TIME))
                .signWith(key)
                .compact();
        return jwt;
    }


    public static String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return (String) claims.get("email");
    }
}


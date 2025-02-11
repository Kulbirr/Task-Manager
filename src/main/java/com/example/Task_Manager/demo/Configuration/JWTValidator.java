package com.example.Task_Manager.demo.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JWTValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Skiping JWT validation for login and register requests
        if (requestURI.startsWith("/auth/login")) {
            System.out.println("Skipping JWT validation for: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = request.getHeader(JWTConstant.JWT_HEADER);

        if(jwt != null){
            try{
                // Extracting email
                String email = JWTProvider.getEmailFromJwtToken(jwt);
                // Parsing the claims from the JWT
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(JWTProvider.key)
                        .build()
                        .parseClaimsJws(jwt.substring(7)) // Strip "Bearer "
                        .getBody();
                // Extracting the role from claims
                List<String> roleNames = claims.get("roles", List.class);
                if (roleNames == null || roleNames.isEmpty()) {
                    throw new BadCredentialsException("Roles are missing or invalid in the token");
                }


                // Creating authorities based on the role
                List<GrantedAuthority> authorities = roleNames.stream()
                        .map(SimpleGrantedAuthority::new) // Convert role name strings to authorities
                        .collect(Collectors.toList());

                // Creating authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                // Setting the authentication context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response); // Proceeding with the filter chain
    }
}

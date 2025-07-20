package com.ifpbpj2.SIMULENEM_backend.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends OncePerRequestFilter {

        @Autowired
        private UserDetailsJwtService jwtUserDetailsService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {
                final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

                if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
                        System.out.println("Null or void JWT token");
                        filterChain.doFilter(request, response);
                        return;
                }

                if (!JwtUtils.isTokenValid(token)) {
                        System.out.println("Invalid or expired JWT token");
                        filterChain.doFilter(request, response);
                        return;
                }

                String username = JwtUtils.getUsernameFromToken(token);

                toAuthentication(request, username);
                filterChain.doFilter(request, response);

        }

        private void toAuthentication(HttpServletRequest request, String username) {
                var userDetails = jwtUserDetailsService.loadUserByUsername(username);

                var authenticationToken = UsernamePasswordAuthenticationToken.authenticated(
                                userDetails, null, userDetails.getAuthorities());

                authenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

}

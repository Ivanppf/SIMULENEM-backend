package com.ifpbpj2.SIMULENEM_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ifpbpj2.SIMULENEM_backend.infra.jwt.AuthorizationFilter;
import com.ifpbpj2.SIMULENEM_backend.infra.jwt.EntryPointAuthentication;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {

        private static final String[] DOCUMENTATION_OPENAPI = {
                        "/docs/index.html",
                        "/docs-park.html", "/docs-park/**",
                        "/v3/api-docs/**",
                        "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
                        "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
        };

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/login", "api/users").permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(handler -> handler
                                                .authenticationEntryPoint(new EntryPointAuthentication()))
                                .build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        @Bean
        AuthorizationFilter jwtAuthorizationFilter() {
                return new AuthorizationFilter();
        }

}
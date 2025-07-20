package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpbpj2.SIMULENEM_backend.infra.jwt.UserDetailsJwtService;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.LoginDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.TokenDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

        private final UserDetailsJwtService userDetailsJwtService;
        private final AuthenticationManager authenticationManager;

        public AuthController(UserDetailsJwtService userDetailsJwtService,
                        AuthenticationManager authenticationManager) {
                this.userDetailsJwtService = userDetailsJwtService;
                this.authenticationManager = authenticationManager;
        }

        @PostMapping("/login")
        public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDto) {

                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDto.username(),
                                loginDto.password());

                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

                var token = userDetailsJwtService.getTokenAuthenticated(loginDto.username());

                return ResponseEntity.ok(token);
        }

}

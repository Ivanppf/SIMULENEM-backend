package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpbpj2.SIMULENEM_backend.business.services.user.UserService;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.user.UserMapper;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.user.UserRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.user.UserResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping
        public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
                var user = userService.create(UserMapper.toUser(request));
                return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
        }
}

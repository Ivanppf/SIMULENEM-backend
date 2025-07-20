package com.ifpbpj2.SIMULENEM_backend.business.services.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpbpj2.SIMULENEM_backend.exception.EntityInUseException;
import com.ifpbpj2.SIMULENEM_backend.exception.SuapUserNotFoundException;
import com.ifpbpj2.SIMULENEM_backend.infra.client.SuapClient;
import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;
import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User.Role;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.user.UserRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.LoginDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.UserPageableSuapDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

        private final UserRepository userRepository;
        private final SuapClient suapClient;
        private final PasswordEncoder passwordEncoder;

        public UserService(UserRepository userRepository, SuapClient suapClient, PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.suapClient = suapClient;
                this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public User create(User user) {
                try {
                        var loginResponse = suapClient.login(new LoginDTO(user.getRegistration(), user.getPassword()));
                        var suapToken = loginResponse.getBody();

                        validateUserExists(user.getRole(), suapToken.access(), user.getRegistration());

                        var encodedPassword = passwordEncoder.encode(user.getPassword());
                        user.setPassword(encodedPassword);
                        return userRepository.save(user);
                } catch (EntityInUseException e) {
                        throw new EntityInUseException(
                                        String.format("User '%s' in use", user.getRegistration()));
                }
        }

        @Transactional(readOnly = true)
        public User getByRegistration(String registration) {
                return userRepository.findByRegistration(registration).orElseThrow(
                                () -> new EntityNotFoundException(
                                                String.format("User '%s' not found", registration)));
        }

        @Transactional(readOnly = true)
        public Role getRoleByRegistration(String registration) {
                return userRepository.findRoleByRegistration(registration);

        }

        private void validateUserExists(Role role, String token, String registration) {
                ResponseEntity<UserPageableSuapDTO> response;

                if (role == Role.ROLE_STUDENT) {
                        response = suapClient.findStudent(token, registration);
                } else if (role == Role.ROLE_EMPLOYEE) {
                        response = suapClient.findEmployee(token, registration);
                } else {
                        throw new IllegalArgumentException("Unsupported role: " + role);
                }

                if (response.getBody().count() == 0) {
                        throw new SuapUserNotFoundException();
                }
        }

}

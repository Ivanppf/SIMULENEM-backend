package com.ifpbpj2.SIMULENEM_backend.business.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User.Role;
import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.EntityInUseException;
import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public User create(User user) {
                try {
                        var encodedPassword = passwordEncoder.encode(user.getPassword());
                        user.setPassword(encodedPassword);
                        return userRepository.save(user);
                } catch (Exception e) {
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

}

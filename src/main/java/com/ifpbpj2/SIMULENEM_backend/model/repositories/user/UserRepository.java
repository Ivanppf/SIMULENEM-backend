package com.ifpbpj2.SIMULENEM_backend.model.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

        Optional<User> findByRegistration(String registration);

        @Query("SELECT u.role FROM User u WHERE u.registration LIKE :registration")
        User.Role findRoleByRegistration(String registration);

}

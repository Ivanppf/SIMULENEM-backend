package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.user;

import org.springframework.beans.BeanUtils;

import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;
import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User.Role;;

public class UserMapper {

        private UserMapper() {
        }

        public static User toUser(UserRequestDTO request) {
                var user = new User();

                BeanUtils.copyProperties(request, user);
                var role = String.format("ROLE_%s", request.role());
                user.setRole(Role.valueOf(role));

                return user;
        }

        public static UserResponseDTO toDto(User user) {
                String role = user.getRole().name().substring("ROLE_".length());

                return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRegistration(), role);
        }

}

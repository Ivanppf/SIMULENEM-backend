package com.ifpbpj2.SIMULENEM_backend.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.business.services.user.UserService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.TokenDTO;

@Service
public class UserDetailsJwtService implements UserDetailsService {

        private final UserService userService;

        public UserDetailsJwtService(UserService userService) {
                this.userService = userService;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                var user = userService.getByRegistration(username);
                return new UserDetailsJwt(user);
        }

        public TokenDTO getTokenAuthenticated(String username) {
                User.Role role = userService.getRoleByRegistration(username);
                return JwtUtils.generate(username, role.name().substring("ROLE_".length()));
        }
}

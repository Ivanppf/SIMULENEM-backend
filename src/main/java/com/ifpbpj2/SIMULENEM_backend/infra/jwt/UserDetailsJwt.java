package com.ifpbpj2.SIMULENEM_backend.jwt;

import org.springframework.security.core.authority.AuthorityUtils;

import com.ifpbpj2.SIMULENEM_backend.model.entities.user.User;

public class UserDetailsJwt extends org.springframework.security.core.userdetails.User {

        private User user;

        public UserDetailsJwt(User user) {
                super(user.getRegistration(), user.getPassword(),
                                AuthorityUtils.createAuthorityList(user.getRole().name()));
                this.user = user;
        }

        public Long getId() {
                return this.user.getId();
        }

        public String getRole() {
                return this.user.getRole().name();
        }
}

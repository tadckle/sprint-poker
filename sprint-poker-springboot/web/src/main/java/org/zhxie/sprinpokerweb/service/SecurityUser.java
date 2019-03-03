package org.zhxie.sprinpokerweb.service;

import com.google.common.collect.Lists;
import org.persistent.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public  class SecurityUser extends User implements UserDetails {

    private final Collection<? extends GrantedAuthority> authorities;

        public SecurityUser(User user) {
            this.setEmail(user.getEmail());
            this.setName(user.getName());
            this.setPassword(user.getPassword());
            authorities = Lists.newArrayList();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return super.getPassword();
        }

        @Override
        public String getUsername() {
            return super.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
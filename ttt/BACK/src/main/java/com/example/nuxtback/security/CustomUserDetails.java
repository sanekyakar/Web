package com.example.nuxtback.security;


import com.example.nuxtback.entity.Role;
import com.example.nuxtback.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<Role> authoritySet;

    public static CustomUserDetails fromUserToUserDetails(User user){
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.password = user.getPassword();
        customUserDetails.username = user.getUsername();
        customUserDetails.authoritySet = user.getRoles();
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

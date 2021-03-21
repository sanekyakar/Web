package com.example.nuxtback.security;


import com.example.nuxtback.entity.User;
import com.example.nuxtback.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(s);
        return CustomUserDetails.fromUserToUserDetails(user);
    }
}

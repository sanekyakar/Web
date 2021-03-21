package com.example.nuxtback.controller;


import com.example.nuxtback.entity.User;
import com.example.nuxtback.security.JwtProvider;
import com.example.nuxtback.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    UserService userService;
    JwtProvider jwtProvider;

    public AuthenticationController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody User user){
        try {
            return ResponseEntity.ok(userService.addUser(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User userFromLogin){
        try{
            User user = userService.findUserByUsernameAndPassword(userFromLogin.getUsername(),userFromLogin.getPassword());
            String token =jwtProvider.generateToken(user.getUsername());
            Map<String, String> map = new HashMap<>();
            map.put("token",token);
            return ResponseEntity.ok(map);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

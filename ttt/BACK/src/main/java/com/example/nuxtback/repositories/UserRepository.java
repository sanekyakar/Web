package com.example.nuxtback.repositories;


import com.example.nuxtback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByUsername(String username);

}

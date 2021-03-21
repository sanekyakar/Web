package com.example.nuxtback.repositories;

import com.example.nuxtback.entity.Busket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Busket,Integer> {
}

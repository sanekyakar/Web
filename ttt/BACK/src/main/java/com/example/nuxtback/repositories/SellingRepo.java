package com.example.nuxtback.repositories;

import com.example.nuxtback.entity.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingRepo extends JpaRepository<Selling,Integer> {
}

package com.example.nuxtback.repositories;

import com.example.nuxtback.entity.SellingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingEntryRepo extends JpaRepository<SellingEntry,Integer> {
}

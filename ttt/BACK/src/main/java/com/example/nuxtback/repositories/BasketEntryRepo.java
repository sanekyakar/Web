package com.example.nuxtback.repositories;

import com.example.nuxtback.entity.BusketEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketEntryRepo extends JpaRepository<BusketEntry,Integer> {
}

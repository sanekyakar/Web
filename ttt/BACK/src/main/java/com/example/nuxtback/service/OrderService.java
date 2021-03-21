package com.example.nuxtback.service;

import com.example.nuxtback.entity.*;
import com.example.nuxtback.repositories.BasketRepo;
import com.example.nuxtback.repositories.SellingEntryRepo;
import com.example.nuxtback.repositories.SellingRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    UserService userService;
    SellingRepo sellingRepo;
    SellingEntryRepo sellingEntryRepo;
    BasketRepo basketRepo;


    public OrderService(UserService userService, SellingRepo sellingRepo, SellingEntryRepo sellingEntryRepo, BasketRepo basketRepo) {
        this.userService = userService;
        this.sellingRepo = sellingRepo;
        this.sellingEntryRepo = sellingEntryRepo;
        this.basketRepo = basketRepo;
    }

    public Selling addSelling(String username, String address){
        User user = userService.findUserByUsername(username);
        Busket busket = user.getBusket();
        Selling selling = new Selling();
        selling.setUser(user);
        selling.setSellingEntrySet(new HashSet<>());
        selling.setAccepted(false);
        selling.setDeleted(false);
        selling.setDetails("Ждет проверки");
        selling.setAddress(address);
        Selling SfromDb =  sellingRepo.save(selling);
        for(BusketEntry busketEntry : busket.getProducts()){
            SellingEntry sellingEntry = new SellingEntry();
            sellingEntry.setCount(busketEntry.getCount());
            sellingEntry.setProduct(busketEntry.getProduct());
            sellingEntry.setSale(busketEntry.getProduct().getSale());
            sellingEntry.setPrice(busketEntry.getProduct().getPrice() * (1 - busketEntry.getProduct().getSale() / 100));
            sellingEntry.setSelling(SfromDb);
            SellingEntry fromDb = sellingEntryRepo.save(sellingEntry);
            selling.getSellingEntrySet().add(fromDb);
        }
        Selling NEW =  sellingRepo.save(SfromDb);
        busket.setProducts(new HashSet<>());
        basketRepo.save(busket);
        userService.saveUser(user);
        return NEW;
    }

    public Selling acceptSelling(Integer id){
        Selling selling = sellingRepo.findById(id).get();
        selling.setAccepted(true);
        selling.setDetails("Одобрен");
        return sellingRepo.save(selling);
    }

    public void deleteSelling(Integer id){
        Selling selling = sellingRepo.findById(id).get();
        selling.setDeleted(true);
        sellingRepo.save(selling);
    }

    public Set<Selling> getAllnotDeleted(){
        List<Selling> set = sellingRepo.findAll();
        return set.stream().filter( selling -> !selling.isDeleted()).collect(Collectors.toSet());
    }
}

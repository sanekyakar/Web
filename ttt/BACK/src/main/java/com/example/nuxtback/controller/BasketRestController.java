package com.example.nuxtback.controller;

import com.example.nuxtback.entity.BusketEntry;
import com.example.nuxtback.entity.User;
import com.example.nuxtback.service.ProductService;
import com.example.nuxtback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@RequestMapping("/basket")
@RestController
public class BasketRestController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @GetMapping("/{token}")
    public ResponseEntity getBasket(@PathVariable String token){
        User user = userService.findUserByUsername(token);
        if(user.getBusket() == null) return ResponseEntity.ok(new HashSet<>());
        Set<BusketEntry> busketEntries = userService.findUserByUsername(token).getBusket().getProducts();
        return ResponseEntity.ok(busketEntries.stream().sorted(Comparator.comparingInt(BusketEntry::getId)));
    }

    @PostMapping("/edit")
    public ResponseEntity editBasket(@RequestBody Map<String,String> map){
        String username = map.get("username");
        int count = Integer.parseInt(map.get("count"));
        int id = Integer.parseInt(map.get("id"));
        userService.editBasket(id,username,count);
        return ResponseEntity.ok(userService.findUserByUsername(username).getBusket().getProducts());
    }

    @PostMapping("/delete")
    public ResponseEntity deleteBasket(@RequestBody Map<String,String> map){
        String username = map.get("username");
        int id = Integer.parseInt(map.get("id"));
        userService.deleteBasket(username,id);
        return ResponseEntity.ok(userService.findUserByUsername(username).getBusket().getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity addToBasket(@RequestBody Map<String,String> map){
        String username = map.get("username");
        int id = Integer.parseInt(map.get("id"));
        userService.addToBasket(id,username,1);
        return ResponseEntity.ok(userService.findUserByUsername(username).getBusket().getProducts());
    }



}

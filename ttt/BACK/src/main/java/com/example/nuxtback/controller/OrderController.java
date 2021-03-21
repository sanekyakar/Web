package com.example.nuxtback.controller;

import com.example.nuxtback.entity.*;
import com.example.nuxtback.repositories.SellingEntryRepo;
import com.example.nuxtback.repositories.SellingRepo;
import com.example.nuxtback.service.OrderService;
import com.example.nuxtback.service.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/order")
public class OrderController {

    SellingRepo sellingRepo;
    OrderService orderService;
    UserService userService;

    public OrderController(SellingRepo sellingRepo, OrderService orderService, UserService userService) {
        this.sellingRepo = sellingRepo;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(orderService.getAllnotDeleted());
    }

    @PostMapping("")
    public ResponseEntity addForUser(@RequestBody Map<String,String> map){
        String code = map.get("code");
        String user = map.get("username");
        orderService.addSelling(user,code);
        User u = userService.findUserByUsername(user);
        return ResponseEntity.ok(orderService.getAllnotDeleted());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteSelling(@PathVariable Integer id){
        orderService.deleteSelling(id);
        return ResponseEntity.ok(orderService.getAllnotDeleted());
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity acceptSelling(@PathVariable Integer id){
        orderService.acceptSelling(id);
        return ResponseEntity.ok(orderService.getAllnotDeleted());
    }


}

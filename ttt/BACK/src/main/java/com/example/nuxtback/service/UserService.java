package com.example.nuxtback.service;


import com.example.nuxtback.entity.*;
import com.example.nuxtback.repositories.BasketEntryRepo;
import com.example.nuxtback.repositories.BasketRepo;
import com.example.nuxtback.repositories.UserRepository;
import com.example.nuxtback.security.JwtProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ProductService productService;
    BasketEntryRepo basketEntryRepo;
    BasketRepo basketRepo;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductService productService, BasketEntryRepo basketEntryRepo, BasketRepo basketRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
        this.basketEntryRepo = basketEntryRepo;
        this.basketRepo = basketRepo;
    }

    public User addUser(User user){
        if(isUnique(user)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            return userRepository.save(user);
        }else{
            throw new RuntimeException("this username already registered");
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User u){
        return userRepository.save(u);
    }

    public User editUser(User user){
        User oldUser = findUserById(user.getId());
        if(user.getUsername() != null) oldUser.setUsername(user.getUsername());
        if(user.getPassword() != null) oldUser.setPassword(user.getPassword());
        return userRepository.save(oldUser);
    }

    public void deleteUser(Integer id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserByUsername(String username){
        Optional<User> userFromDb = userRepository.findUserByUsername(username);
        if(userFromDb.isPresent()){
            return userFromDb.get();
        }else{
            throw new UsernameNotFoundException("cant find user with username : " + username);
        }
    }

    public User findUserByUsernameAndPassword(String username, String password){
        try {
            User user = findUserByUsername(username);
            if(passwordEncoder.matches(password,user.getPassword())) return user;
        }catch (Exception e){
            throw new UsernameNotFoundException("cant login with : " + username + " - " + password);
        }
        return null;
    }

    public User findUserById(Integer id){
        Optional<User> userFromDb = userRepository.findUserById(id);
        if(userFromDb.isPresent()){
            return userFromDb.get();
        }else{
            throw new UsernameNotFoundException("cant find user with id : " + id);
        }
    }

    public boolean isUnique(User user){
        for(User u : userRepository.findAll()){
            if(u.getUsername().equals(user.getUsername())) return false;
        }
        return true;
    }

    public void addToBasket(Integer id, String username,Integer count) {
        User user = findUserByUsername(username);
        Product product = productService.getById(id);
        if(user.getBusket()== null){ //creating basket if null
            Busket busket = new Busket();
            busket.setUser(user);
            busket.setProducts(new HashSet<>());
            Busket fromDb = basketRepo.save(busket);
            user.setBusket(fromDb);
            userRepository.save(user);
        }
        for(BusketEntry entry : user.getBusket().getProducts()){//увеличиваем
            if(entry.getProduct().getId().equals(product.getId())){
                if(product.getCount().equals(entry.getCount())) return;
                if(product.getCount() >= entry.getCount() + count){
                    entry.setCount(entry.getCount() + count);
                    basketEntryRepo.save(entry);
                    return;
                }
            }
        }
        if(product.getCount() >= count){ //или создаем если не нашли
            BusketEntry busketEntry = new BusketEntry();
            busketEntry.setCount(count);
            busketEntry.setProduct(product);
            BusketEntry fromDb  = basketEntryRepo.save(busketEntry);
            user.getBusket().getProducts().add(fromDb);
            basketRepo.save(user.getBusket());
            userRepository.save(user);
        }
    }

    public void editBasket(Integer id, String username,Integer count){
        User user = findUserByUsername(username);
        Product product = productService.getById(id);
        for(BusketEntry entry : user.getBusket().getProducts()){
            if(entry.getProduct().getId().equals(product.getId())){
                if(product.getCount() >= count){
                    entry.setCount(count);
                    basketEntryRepo.save(entry);
                    return;
                }
                if(product.getCount().equals(entry.getCount())) return;
            }
        }
    }

    public void deleteBasket(String username, int id) {
        User user = findUserByUsername(username);
        for(BusketEntry busketEntry : user.getBusket().getProducts()){
            if(busketEntry.getProduct().getId().equals(id)){
                user.getBusket().getProducts().remove(busketEntry);
                basketEntryRepo.delete(busketEntry);
                userRepository.save(user);
                return;
            }
        }
    }
}

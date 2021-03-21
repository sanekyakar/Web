package com.example.nuxtback.controller;

import com.example.nuxtback.entity.Product;

import com.example.nuxtback.service.FileService;
import com.example.nuxtback.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class ProductController {
    ProductService productService;
    FileService fileService;

    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }



    @GetMapping("/products")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(productService.getAll());
    }


    @PutMapping("/products")
    public ResponseEntity editProducts(Product product){
        try{
            Product fr = productService.getById(product.getId());
            product.setImage(fr.getImage());
            Product pr = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body(pr);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/products")
    public ResponseEntity addProducts(Product product,MultipartFile file){
        try{
            product.setImage(fileService.saveFile(file));
            Product pr = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(pr);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


}

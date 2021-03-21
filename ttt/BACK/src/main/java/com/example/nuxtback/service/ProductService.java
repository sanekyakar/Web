package com.example.nuxtback.service;

import com.example.nuxtback.entity.Product;
import com.example.nuxtback.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product getById(Integer id){
        Optional<Product> optionalPackage = productRepo.findById(id);
        if(optionalPackage.isPresent()){
            return optionalPackage.get();
        }else{
            throw new RuntimeException("cant find package with id : " + id);
        }
    }

    public List<Product> getAll(){
        return productRepo.findAll();
    }



    public Product saveProduct(Product p){
        return productRepo.save(p);
    }

    public Product editProduct(Product product,Integer id){
        Product prFormDb = getById(id);
        return productRepo.save(prFormDb);
    }

    public void deleteProduct(Integer id){
        Product prFormDb = getById(id);
        prFormDb.setDeleted(true);
        productRepo.save(prFormDb);
    }
}

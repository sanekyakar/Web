package com.example.nuxtback.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String author;
    private String text;
    private Integer pages;
    private float price;
    private Integer sale;
    private boolean deleted;
    private String image;
    private Integer count;

}

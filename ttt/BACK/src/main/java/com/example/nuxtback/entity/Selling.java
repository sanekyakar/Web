package com.example.nuxtback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "selling")
public class Selling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany( fetch = FetchType.EAGER)
    private Set<SellingEntry> sellingEntrySet;
    private String details;
    private String address;
    boolean accepted;
    boolean deleted;


    public float calculatePrice(){
        float price = 0;
        for(SellingEntry entry : sellingEntrySet){
            price += entry.getPrice() * entry.getCount() * (1 - ((float)entry.getSale() / 100));
        }
        return price;
    }


    public Selling() {
    }
}

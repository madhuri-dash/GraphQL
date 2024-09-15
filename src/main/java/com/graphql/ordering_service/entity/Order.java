package com.graphql.ordering_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String name;
    private String category;
    private int price;
    private int stock;


    public Order(String name, String category, int price, int stock) {
        this.name=name;
        this.category=category;
        this.price=price;
        this.stock=stock;
    }
}
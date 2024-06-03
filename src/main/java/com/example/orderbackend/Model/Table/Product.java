package com.example.orderbackend.Model.Table;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class Product {
    private Long id;
    private String name;
    private float price;
    private int quantity;
}

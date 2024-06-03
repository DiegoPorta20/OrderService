package com.example.orderbackend.Model.Colection;

import com.example.orderbackend.Model.Table.Product;
import com.example.orderbackend.Model.Table.Customer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Document(collection = "order")
public class Order {

    @Id
    private String id;
    private List<Product> products;
    private Customer customer;
    private float total;
}

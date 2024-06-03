package com.example.orderbackend.Model.Table;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Table("customer")
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}

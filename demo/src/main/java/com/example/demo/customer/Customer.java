package com.example.demo.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "firstName es obligatorio")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "lastName es obligatorio")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "email es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;
}

package com.cafeteria.api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "clientes")
@Data
public class Clients {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    private long cpf;
    private String name;
    private String email;
    private String tel;

}

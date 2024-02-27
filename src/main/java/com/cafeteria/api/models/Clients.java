package com.cafeteria.api.models;

import com.cafeteria.api.controllers.Cpf;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
public class Clients {

    @Id
    private long id;

    private int cpf;
    private String name;
    private String email;
    private String password;

}

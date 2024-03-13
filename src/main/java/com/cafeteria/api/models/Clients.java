package com.cafeteria.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CPF
    private String cpf;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String tel;

}

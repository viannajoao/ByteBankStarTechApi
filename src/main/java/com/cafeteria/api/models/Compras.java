package com.cafeteria.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;



@Entity
@Table(name = "compras")
@Data
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "Data")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String date;
    @NotBlank
    private String categoria;
    @NotBlank
    @Column(name = "Cartao")
    private String numeroCartao;
    @NotBlank
    private String valor;
    @NotBlank
    private String estabelecimento;


    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        date = agora.format(formatter);
    }



}

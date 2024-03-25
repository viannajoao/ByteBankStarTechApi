package com.cafeteria.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @Column(name = "horario")
    private LocalDateTime date;
    @NotBlank
    private String categoria;
    @NotBlank
    @Column(name = "Cartao")
    private String cartao;

    private double valor;
    @NotBlank
    private String estabelecimento;
    @ManyToOne
    @JoinColumn(name = "credito_id", referencedColumnName = "id")
    private Credito credits_id;



    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }





}

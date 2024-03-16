package com.cafeteria.api.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
@Data
@Component
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @Pattern(regexp = "\\d{16}", message = "A variável deve conter exatamente 16 caracteres numéricos.")
    private String numCartao;
    @Formula("(SELECT c.name FROM Clients c WHERE c.id = cliente_id)")
    private String client;

    private String validade;

//    @Pattern(regexp = "\\d{3}", message = "A variável deve conter exatamente 16 caracteres numéricos.")
    private String cv;

    private BigDecimal limity;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Clients clients_id;

    public Credito() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate validadeCartao = dataAtual.plusYears(4).plusMonths(6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        this.validade = validadeCartao.format(formatter);

        this.numCartao = GerarNumCard();
        this.cv = GerarCv();

    }

    public String GerarNumCard() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digito = random.nextInt(10); // Gera um dígito de 0 a 9
            numero.append(digito);
        }
        return numero.toString();
    }

    public String GerarCv() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int digito = random.nextInt(10); // Gera um dígito de 0 a 9
            numero.append(digito);
        }
        return numero.toString();
    }

//    public String getClient(){
//        String nameClient = this.clients_id.getName();
//        return nameClient;
//    }




}

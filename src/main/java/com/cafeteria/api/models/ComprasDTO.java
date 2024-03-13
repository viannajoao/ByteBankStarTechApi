package com.cafeteria.api.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ComprasDTO {

    private String categoria;
    private double valor;
    private LocalDateTime date;

    public ComprasDTO(String categoria, double valor, LocalDateTime date){
        this.categoria = categoria;
        this.valor = valor;
        this.date = date;
        this.getDate();
    }

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return this.date.format(formatter);
    }

}

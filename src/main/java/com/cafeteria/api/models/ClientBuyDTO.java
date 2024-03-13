package com.cafeteria.api.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class ClientBuyDTO {
    private UUID clienteId;
    private String nomeCliente;
    private LocalDateTime date;


    public ClientBuyDTO(UUID clienteId, String nomeCliente, LocalDateTime date){
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.date = date;
//        this.getDate();
    }

//    public String getDate(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        return this.date.format(formatter);
//    }

}

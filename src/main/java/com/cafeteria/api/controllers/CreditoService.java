package com.cafeteria.api.controllers;

import com.cafeteria.api.models.Credito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CreditoService {

    Credito card = new Credito();



    public String GerarCv() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int digito = random.nextInt(10); // Gera um dígito de 0 a 9
            numero.append(digito);
        }
        return numero.toString();
    }


}

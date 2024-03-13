package com.cafeteria.api.models;

import lombok.Data;

@Data
public class RelatorioDTO {
    private String clienteId;
    private String nomeCliente;
    private Long totalCompras;

    public RelatorioDTO(String string, String nomeCliente, Long totalCompras) {
    }

    // Construtor, getters e setters
}


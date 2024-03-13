package com.cafeteria.api.controllers;

import com.cafeteria.api.models.ClientBuyDTO;
import com.cafeteria.api.models.Clients;
import com.cafeteria.api.models.Compras;
import com.cafeteria.api.repository.ValidUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientService {

    @Autowired
    private ValidUser validUser;

    public boolean userExist(String email){
        Clients client = validUser.findByEmail(email);
                return client != null;
    }

    public List<Object[]> getClientesMaisCompraram() {
        return validUser.findClientesMaisCompraram();
    }

    public List<Object[]> findSumValorByCategoria() {
        return validUser.findSumValorByCategoria();
    }

    public List<Object[]> getClientesMaisGastaram() {
        return validUser.findClientesMaisGastaram();
    }

    public List<Object[]> getClientesNaoCompraramNada() {
        return validUser.findNaoCompraramNada();
    }


}

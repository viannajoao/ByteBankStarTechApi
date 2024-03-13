package com.cafeteria.api.controllers;

import com.cafeteria.api.models.Clients;
import com.cafeteria.api.repository.ValidUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ValidUser validUser;

    public boolean userExist(String email){
        Clients client = validUser.findByEmail(email);
                return client != null;
    }


}

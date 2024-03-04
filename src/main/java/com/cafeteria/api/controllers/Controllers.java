package com.cafeteria.api.controllers;

import com.cafeteria.api.models.Clients;
import com.cafeteria.api.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class Controllers {

//    @GetMapping("/login")
//    public String login(){
//        return "Tela de login";
//
//    }
    @Autowired
    private Repository repository;

    @PostMapping("/cadastrar")
    public Clients cadastrar(@RequestBody Clients c){
        return repository.save(c);
    }


    @GetMapping("/")
    public Iterable<Clients> PrincipalScreen(){
        return repository.findAll();
    }


}

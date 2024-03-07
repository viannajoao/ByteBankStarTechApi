package com.cafeteria.api.controllers;

import com.cafeteria.api.models.Clients;
import com.cafeteria.api.models.Credito;
import com.cafeteria.api.repository.Repository;
import com.cafeteria.api.repository.RepositoryCard;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class Controllers {

    @Autowired
    private Repository repository;

    @Autowired
    private ClientService clientService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid Clients c) {
        if (clientService.userExist(c.getEmail())) {
            return ResponseEntity.badRequest().body("Usuario ja existente");
        }

         repository.save(c);

        return ResponseEntity.ok("Usuario cadastrado");
    }


    @GetMapping("/")
    public Iterable<Clients> PrincipalScreen() {

        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable("id") UUID id) {
        Clients client = repository.findById(id).orElse(null);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{id}")
    public Clients edite(@PathVariable("id") UUID id, @RequestBody Clients c) {
        return repository.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }



    //FUNCOES CRUD PARA CARTOES

    @Autowired
    private Credito card;


    @Autowired
    private RepositoryCard repositoryCard;

    @PostMapping("/cadastrarCartoes")
    public Credito cadastrarCartao(@RequestBody Credito credito) {
        String validade = card.getValidade();
       return repositoryCard.save(credito);

    }

    @GetMapping("/cartoes")
    public Iterable<Credito> Cartoes() {

        return repositoryCard.findAll();
    }


}

package com.cafeteria.api.controllers;

import com.cafeteria.api.models.Clients;
import com.cafeteria.api.repository.Repository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/")
    public Clients edite(@RequestBody Clients c) {
        return repository.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }


}

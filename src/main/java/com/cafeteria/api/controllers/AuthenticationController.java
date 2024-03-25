package com.cafeteria.api.controllers;

import com.cafeteria.api.infra.security.TokenService;
import com.cafeteria.api.models.AuthenticationDTO;
import com.cafeteria.api.models.LoginResponseDTO;
import com.cafeteria.api.models.RegisteredDTO;
import com.cafeteria.api.repository.GerenteRepository;
import com.cafeteria.api.security.Gerente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Gerente) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisteredDTO data){
        if(this.gerenteRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword =  new BCryptPasswordEncoder().encode(data.password());
        Gerente newGerente = new Gerente(data.login(), encryptedPassword, data.role());

        this.gerenteRepository.save(newGerente);
        System.out.println(newGerente);
        return ResponseEntity.ok().build();
    }

}

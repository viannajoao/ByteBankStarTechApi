package com.cafeteria.api.repository;

import com.cafeteria.api.security.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface GerenteRepository extends JpaRepository<Gerente, String> {

    UserDetails findByLogin(String login);
}

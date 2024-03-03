package com.cafeteria.api.repository;

import com.cafeteria.api.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidUser extends JpaRepository<Clients, Long> {
    Clients findByEmail(String email);
}

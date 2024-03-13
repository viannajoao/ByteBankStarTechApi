package com.cafeteria.api.repository;

import com.cafeteria.api.models.Compras;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositoryCompras extends JpaRepository<Compras, UUID> {

    List<Compras> findByCartao(String cartao);

}

package com.cafeteria.api.repository;

import com.cafeteria.api.models.Credito;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryCard extends CrudRepository<Credito, UUID> {
//    Optional<Credito> findByNumeroCartao(String numeroCartao);
}

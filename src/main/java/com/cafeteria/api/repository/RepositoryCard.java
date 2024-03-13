package com.cafeteria.api.repository;

import com.cafeteria.api.models.Credito;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryCard extends CrudRepository<Credito, Long> {
}

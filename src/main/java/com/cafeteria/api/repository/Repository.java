package com.cafeteria.api.repository;

import com.cafeteria.api.models.Clients;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface Repository extends CrudRepository<Clients, UUID> {

}

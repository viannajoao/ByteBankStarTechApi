package com.cafeteria.api.repository;

import com.cafeteria.api.models.Clients;
import org.springframework.data.repository.CrudRepository;


public interface Repository extends CrudRepository<Clients, Long> {

}

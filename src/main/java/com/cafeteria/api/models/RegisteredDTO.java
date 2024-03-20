package com.cafeteria.api.models;

import com.cafeteria.api.security.GerenteRole;

public record RegisteredDTO(String login, String password, GerenteRole role) {
}

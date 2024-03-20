package com.cafeteria.api.security;

public enum GerenteRole {

    ADMIN("admin"),

    USER("user");
    private String role;

    GerenteRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}

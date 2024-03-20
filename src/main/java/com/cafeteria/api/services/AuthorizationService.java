package com.cafeteria.api.services;

import com.cafeteria.api.repository.GerenteRepository;
import com.cafeteria.api.security.GerenteRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    GerenteRepository gerenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return gerenteRepository.findByLogin(username);
    }
}

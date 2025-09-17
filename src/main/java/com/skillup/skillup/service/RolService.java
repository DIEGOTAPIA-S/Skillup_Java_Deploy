package com.skillup.skillup.service;

import com.skillup.skillup.model.Rol;
import com.skillup.skillup.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService{

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> obtenerTodosLosRoles(){
        return rolRepository.findAll();
    }
}
package com.skillup.skillup.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.UsuarioRepository;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository repo;

    //INYECCION DEL REPOSITORIO EN EL CONSTRUCTOR
    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    //GET/usuarios -> devuelve todos los usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return repo.findAll();
    }

    //POST /usuarios -> crea un nuevo usuario
    @PostMapping
    public Usuario creaUsuario(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }

}
package com.skillup.skillup.service;

import com.skillup.skillup.model.Usuario;
import com.skillup.skillup.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EmailService emailService;


    public List<Usuario> findAll() {
        return (List<Usuario>)usuariosRepository.findAll();
    }

    public Optional<Usuario> findById(String id) {
        return usuariosRepository.findById(id);
    }

    @Transactional
    public Usuario save(Usuario usuario) {

        Usuario nuevo = usuariosRepository.save(usuario);


        emailService.sendWelcomeEmail(
                usuario.getCorreo(),
                usuario.getIdentificacion()
        );

        return nuevo;
    }

    @Transactional
    public void deleteByIdRol(String idRol) {
        usuariosRepository.deleteById(idRol);
    }

    public List<Usuario> findByRol(Integer idRol) {
        return usuariosRepository.findByIdRol(idRol);
    }
}
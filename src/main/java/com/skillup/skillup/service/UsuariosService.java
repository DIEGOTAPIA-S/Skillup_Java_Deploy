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
        System.out.println("DEBUG: UsuariosService intentando guardar en DB...");
        Usuario nuevo = usuariosRepository.save(usuario);
        System.out.println("DEBUG: Guardado en DB OK. Intentando enviar correo...");

        try {
            emailService.sendWelcomeEmail(
                    usuario.getCorreo(),
                    usuario.getIdentificacion()
            );
            System.out.println("DEBUG: Llamada a correo realizada.");
        } catch (Exception e) {
            System.err.println("DEBUG: Error (no crítico) enviando correo: " + e.getMessage());
            // No relanzamos para que no haga rollback si falla solo el correo
        }

        return nuevo;
    }

    @Transactional
    public void eliminarUsuario(String identificacion) {
        System.out.println("DEBUG: UsuariosService eliminando identificación: " + identificacion);
        usuariosRepository.deleteById(identificacion);
    }

    public List<Usuario> findByRol(Integer idRol) {
        return usuariosRepository.findByIdRol(idRol);
    }
}
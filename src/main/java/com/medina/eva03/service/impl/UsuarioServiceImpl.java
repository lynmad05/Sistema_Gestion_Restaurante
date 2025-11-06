package com.medina.eva03.service.impl;

import com.medina.eva03.model.Usuario;
import com.medina.eva03.repository.UsuarioRepository;
import com.medina.eva03.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Necesario para cifrar
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectar BCryptPasswordEncoder

    @Override
    public Usuario guardar(Usuario usuario) {
        // Si el usuario es nuevo O si la contraseña ha cambiado y no está cifrada (simple check)
        if (usuario.getIdUsuario() == null || !usuario.getContrasena().startsWith("$2a$")) {
            // Cifra la contraseña antes de guardar
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
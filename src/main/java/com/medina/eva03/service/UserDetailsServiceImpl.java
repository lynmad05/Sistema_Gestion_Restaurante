package com.medina.eva03.service;

import com.medina.eva03.model.Usuario;
import com.medina.eva03.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Mapea el rol a una autoridad para Spring Security (ej: ROL_ADMIN)
        String rolConPrefijo = "ROLE_" + usuario.getRol().toUpperCase();

        return new User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(), // Contraseña cifrada de la DB
                usuario.getEstado(),    // Si está activo
                true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(rolConPrefijo))
        );
    }
}
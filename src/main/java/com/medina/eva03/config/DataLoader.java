package com.medina.eva03.config;

import com.medina.eva03.model.Usuario;
import com.medina.eva03.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Si no existe un usuario ADMIN, lo crea.
            if (usuarioRepository.findByNombreUsuario("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("admin");
                // Contraseña "123" cifrada con BCrypt (RNF1)
                admin.setContrasena(passwordEncoder.encode("123"));
                admin.setRol("ADMIN");
                admin.setEstado(true);
                usuarioRepository.save(admin);
                System.out.println("✅ Usuario ADMIN inicial creado. Contraseña: 123");
            }
        };
    }
}
package com.medina.eva03.config;

import com.medina.eva03.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // <-- ¡Añadido!
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Habilita el uso de @PreAuthorize en los métodos del Controller (Buenas Prácticas)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // RNF1: Bean para cifrar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // RNF2 y RNF3: Configuración de restricciones de acceso y formulario de login
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize

                        // 1. Rutas públicas (Login, Estilos, etc.)
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()

                        // 2. Restricciones de Acceso por Rol (Implementación de la Matriz de Seguridad)

                        // ADMIN: Gestión de Clientes, Mesas, Usuarios, Inventario, Bitácora
                        .requestMatchers("/clientes/**", "/mesas/**", "/usuarios/**", "/bitacora/**", "/inventario/**").hasRole("ADMIN")

                        // MOZO/COCINERO/ADMIN: Gestión de Pedidos
                        .requestMatchers("/pedidos/**").hasAnyRole("MOZO", "COCINERO", "ADMIN")

                        // CAJERO/ADMIN: Gestión de Ventas
                        .requestMatchers("/ventas/**").hasAnyRole("CAJERO", "ADMIN")

                        // RNF2: Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // Redirige al menú principal después del login
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/login?logout")
                );
        return http.build();
    }
}
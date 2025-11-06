package com.medina.eva03.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario; // PK

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    // RNF1: La contraseña se almacenará CIFRADA (BCrypt)
    @Column(nullable = false)
    private String contrasena;

    // ROL: admin, mozo, cajero, cocinero
    @Column(nullable = false)
    private String rol;

    private Boolean estado = true; // Activo/Inactivo
}
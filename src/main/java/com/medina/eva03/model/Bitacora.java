package com.medina.eva03.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
@Data
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBitacora;

    @Column(nullable = false)
    private String tablaAfectada;

    @Column(nullable = false)
    private String accion; // CREAR, ACTUALIZAR, ELIMINAR

    @Column(length = 500)
    private String detalle;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

     private Integer idUsuario;
}
package com.medina.eva03.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesa")
@Data
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING) // Define cómo se guarda el Enum en la DB
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.DISPONIBLE; // Estado por defecto
}
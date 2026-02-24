package com.rojas.dev.tvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rojas.dev.tvc.enumClass.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;

    @Column(unique = true)
    private String correo;

    private String contraseña;

    private String identificacion;

    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private String municipio;

    private String departamento;

    private String fechaCreacion;

    private String state;

    private String vereda;
}

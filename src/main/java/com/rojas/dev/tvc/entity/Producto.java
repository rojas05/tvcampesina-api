package com.rojas.dev.tvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idCategoriaProducto")
    private CategoriaProducto categoria;

    @ManyToOne
    @JoinColumn(name = "idComerciante")
    private Comerciante comerciante;

    private Integer precio;

    private Integer stock;

    private String unidadMedida;

    private String imagenUrl;

    private String fechaRegistro;
}


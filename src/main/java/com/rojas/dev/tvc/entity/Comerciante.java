package com.rojas.dev.tvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rojas.dev.tvc.enumClass.EstadoComerciante;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comerciante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComerciante;

    @OneToOne
    @JoinColumn(name = "idUsuario", unique = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idCategoriaComerciante")
    private CategoriaComerciante categoria;

    private String nombreNegocio;

    private String direccion;

    private String telefonoContacto;

    @Enumerated(EnumType.STRING)
    private EstadoComerciante estado;

    private String fechaRegistro;

    private String img;

    private Integer percent;

    // --- Campos para Payouts (agregar)
    // Código del banco (según estandar del PSP o Wompi)
    private String bankCode;            // ej "000" o "BANK_CODE"
    private String accountNumber;       // número de cuenta
    private String accountType;         // "checking", "savings" etc
    private String accountOwnerName;    // nombre del titular
    private String accountDocument;     // documento del titular
    private String legalIdType;    // typo de documento del titular
}

package com.rojas.dev.tvc.dto;

import lombok.Data;

@Data
public class UpdateComercianteRequest {

    private String nombreNegocio;

    private String direccion;

    private String telefonoContacto;

    private Integer percent;

    private String bankCode;

    private String accountNumber;

    private String accountType;

    private String accountOwnerName;

    private String accountDocument;
}
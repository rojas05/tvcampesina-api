package com.rojas.dev.tvc.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String nombre;
    private String identificacion;
    private String telefono;
    private String municipio;
    private String departamento;
}
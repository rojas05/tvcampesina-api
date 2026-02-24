package com.rojas.dev.tvc.auth;

import com.rojas.dev.tvc.enumClass.TipoUsuario;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String correo;
    private String contraseña;
    private String identificacion;
    private String telefono;
    private TipoUsuario tipoUsuario;
    private String municipio;
    private String departamento;
    private String vereda;
}


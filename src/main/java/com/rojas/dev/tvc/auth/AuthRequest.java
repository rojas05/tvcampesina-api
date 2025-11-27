package com.rojas.dev.tvc.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String correo;
    private String contraseña;
}


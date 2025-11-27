package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    ResponseEntity<?> obtenerUsuarioPorId(Integer id);

    Usuario buscarPorCorreo(String correo);
}

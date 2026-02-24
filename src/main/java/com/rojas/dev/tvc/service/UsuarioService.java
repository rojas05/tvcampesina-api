package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.dto.UpdateUserRequest;
import com.rojas.dev.tvc.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    ResponseEntity<?> obtenerUsuarioPorId(Integer id);

    Usuario buscarPorCorreo(String correo);

    void actualizarUsuario(Integer idUsuario, UpdateUserRequest request);

    void actualizarState(Integer idUsuario, String state);
}

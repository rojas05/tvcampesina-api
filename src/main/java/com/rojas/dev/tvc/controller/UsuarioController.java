package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.dto.UpdateStateRequest;
import com.rojas.dev.tvc.dto.UpdateUserRequest;
import com.rojas.dev.tvc.entity.Usuario;
import com.rojas.dev.tvc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // 🔹 Actualizar datos generales
    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody UpdateUserRequest request
    ) {
        usuarioService.actualizarUsuario(idUsuario, request);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    // 🔹 Actualizar solo el state
    @PatchMapping("/{idUsuario}/state")
    public ResponseEntity<?> actualizarState(
            @PathVariable Integer idUsuario,
            @RequestBody UpdateStateRequest request
    ) {

        usuarioService.actualizarState(idUsuario, request.getState());
        return ResponseEntity.ok("State actualizado correctamente");
    }
}

package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.dto.UpdateComercianteRequest;
import com.rojas.dev.tvc.entity.Comerciante;
import com.rojas.dev.tvc.enumClass.EstadoComerciante;
import com.rojas.dev.tvc.service.ComercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comerciantes")
public class ComercianteController {

    @Autowired
    private ComercianteService comercianteService;

    // 1. Insertar comerciante
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Comerciante comerciante) {
        return comercianteService.insertarComerciante(comerciante);
    }

    // 2. Obtener comerciante por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return comercianteService.obtenerPorIdUsuario(idUsuario);
    }

    // 3. Obtener comerciantes filtrados por categoría y municipio
    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrar(
            @RequestParam String categoria,
            @RequestParam String municipio) {
        return comercianteService.filtrarPorCategoriaYMunicipio(categoria, municipio);
    }



    // 4. Actualizar todo el comerciante
    @PutMapping("/{idComerciante}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer idComerciante,
            @RequestBody Comerciante comerciante) {
        return comercianteService.actualizarComerciante(idComerciante, comerciante);
    }

    // 4. Actualizar estado del comerciante
    @PatchMapping("/admin/{id}/estado")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoComerciante nuevoEstado) {
        boolean actualizado = comercianteService.actualizarEstado(id, nuevoEstado);
        if (actualizado) {
            return ResponseEntity.ok("Estado actualizado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar el estado.");
        }
    }

    @GetMapping("/admin/state/{state}")
    public ResponseEntity<?> getState(
            @PathVariable EstadoComerciante state) {
        return comercianteService.obtenerPorEstado(state);
    }

    // 5. Obtener comerciante por ID de usuario
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return comercianteService.obtenerPorId(id);
    }

    @PutMapping("/update/{idComerciante}")
    public ResponseEntity<?> actualizarComerciante(
            @PathVariable Integer idComerciante,
            @RequestBody UpdateComercianteRequest request
    ) {

        comercianteService.actualizarComerciante(idComerciante, request);

        return ResponseEntity.ok(Map.of(
                "message", "Comerciante actualizado correctamente"
        ));
    }
}

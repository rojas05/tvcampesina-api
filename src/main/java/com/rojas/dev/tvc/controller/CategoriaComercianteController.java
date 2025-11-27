package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.entity.CategoriaComerciante;
import com.rojas.dev.tvc.service.CategoriaComercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categorias-comerciante")
public class CategoriaComercianteController {

    @Autowired
    private CategoriaComercianteService service;

    // 1. Crear
    @PostMapping
    public ResponseEntity<CategoriaComerciante> crear(@RequestBody CategoriaComerciante categoria) {
        return ResponseEntity.ok(service.crear(categoria));
    }

    // 2. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 3. Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaComerciante> actualizar(
            @PathVariable Integer id,
            @RequestBody CategoriaComerciante categoria) {
        return ResponseEntity.ok(service.actualizar(id, categoria));
    }

    // 4. Listar todas
    @GetMapping
    public ResponseEntity<List<CategoriaComerciante>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}

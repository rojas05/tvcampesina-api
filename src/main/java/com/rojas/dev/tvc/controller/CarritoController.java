package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.entity.Carrito;
import com.rojas.dev.tvc.entity.ItemCarrito;
import com.rojas.dev.tvc.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // 1. Crear un carrito
    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.crearCarrito(carrito));
    }

    // 2. Agregar item al carrito
    @PostMapping("/item")
    public ResponseEntity<ItemCarrito> agregarItem(@RequestBody ItemCarrito item) {
        return ResponseEntity.ok(carritoService.agregarItem(item));
    }

    // 3. Eliminar un item
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer idItem) {
        carritoService.eliminarItem(idItem);
        return ResponseEntity.noContent().build();
    }

    // 4. Actualizar cantidad de un item
    @PutMapping("/item/{idItem}")
    public ResponseEntity<ItemCarrito> actualizarCantidad(
            @PathVariable Integer idItem,
            @RequestParam Integer cantidad) {
        return ResponseEntity.ok(carritoService.actualizarCantidadItem(idItem, cantidad));
    }

    // 5. Listar items por id del tendero
    @GetMapping("/items/tendero/{idTendero}")
    public ResponseEntity<List<ItemCarrito>> listarPorTendero(@PathVariable Integer idTendero) {
        return ResponseEntity.ok(carritoService.obtenerItemsPorTendero(idTendero));
    }

    // 6. Eliminar carrito por ID
    @DeleteMapping("/{idCarrito}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Integer idCarrito) {
        carritoService.eliminarCarrito(idCarrito);
        return ResponseEntity.noContent().build();
    }
}


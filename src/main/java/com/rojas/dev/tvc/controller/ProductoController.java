package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.entity.Producto;
import com.rojas.dev.tvc.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // 1. Agregar producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        return productoService.agregarProducto(producto);
    }

    // 2. Listar productos por id del comerciante
    @GetMapping("/comerciante/{idComerciante}")
    public ResponseEntity<?> listarPorComerciante(@PathVariable Integer idComerciante) {
        return productoService.listarProductosPorComerciante(idComerciante);
    }

    // 3. Obtener producto por ID
    @GetMapping("/{idProducto}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idProducto) {
        return productoService.obtenerProductoPorId(idProducto);
    }

    // 4. Actualizar producto
    @PutMapping("/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer idProducto, @RequestBody Producto producto) {
        return productoService.actualizarProducto(idProducto, producto);
    }

    // 3. Obtener producto por ID
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idProducto) {
        return productoService.delete(idProducto);
    }
}

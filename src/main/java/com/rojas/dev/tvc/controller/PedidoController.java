package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.entity.ItemPedido;
import com.rojas.dev.tvc.entity.Pedido;
import com.rojas.dev.tvc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 1. Agregar un nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.agregarPedido(pedido));
    }

    // 2. Obtener pedidos por ID de tendero (cliente), ordenados por estado
    @GetMapping("/tendero/{idTendero}")
    public ResponseEntity<List<Pedido>> obtenerPorTendero(@PathVariable Integer idTendero) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorTendero(idTendero));
    }

    // 3. Obtener pedidos por ID de comerciante (filtrando por productos vendidos por él)
    @GetMapping("/comerciante/{idComerciante}")
    public ResponseEntity<List<Pedido>> obtenerPorComerciante(@PathVariable Integer idComerciante) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorComerciante(idComerciante));
    }

    // 4. Actualizar el estado de un pedido
    @PutMapping("/{idPedido}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer idPedido, @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(idPedido, estado));
    }

    // 5. Crear un pedido con items y actualizar stock
    @PostMapping("/con-items")
    public ResponseEntity<Pedido> crearPedidoConItems(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.agregarPedidoConItems(pedido));
    }

    // 6. Obtener items de un pedido por su ID
    @GetMapping("/{idPedido}/items")
    public ResponseEntity<List<ItemPedido>> obtenerItemsDePedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(pedidoService.obtenerItemsDePedido(idPedido));
    }

    // 7. Actualizar estado y fecha de entrega
    @PutMapping("/{idPedido}/actualizar-estado-entrega")
    public ResponseEntity<Pedido> actualizarEstadoYFechaEntrega(
            @PathVariable Integer idPedido,
            @RequestParam Boolean estado,
            @RequestParam String fechaEntrega
    ) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoYFechaEntrega(idPedido, estado, fechaEntrega));
    }

}

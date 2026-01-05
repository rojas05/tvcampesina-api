package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.entity.Producto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductoService {

    ResponseEntity<?> agregarProducto(Producto producto);

    ResponseEntity<?> listarProductosPorComerciante(Integer idComerciante);

    ResponseEntity<?> obtenerProductoPorId(Integer idProducto);

    ResponseEntity<?> actualizarProducto(Integer idProducto, Producto productoActualizado);

    ResponseEntity<?> delete(Integer idProducto);
}

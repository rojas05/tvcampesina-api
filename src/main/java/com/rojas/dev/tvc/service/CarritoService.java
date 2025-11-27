package com.rojas.dev.tvc.service;


import com.rojas.dev.tvc.entity.Carrito;
import com.rojas.dev.tvc.entity.ItemCarrito;

import java.util.List;

public interface CarritoService {
    Carrito crearCarrito(Carrito carrito);
    ItemCarrito agregarItem(ItemCarrito item);
    void eliminarItem(Integer idItem);
    ItemCarrito actualizarCantidadItem(Integer idItem, Integer nuevaCantidad);
    List<ItemCarrito> obtenerItemsPorTendero(Integer idTendero);
    void eliminarCarrito(Integer idCarrito);
}


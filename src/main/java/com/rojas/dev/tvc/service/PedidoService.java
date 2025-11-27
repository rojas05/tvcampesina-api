package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.entity.ItemPedido;
import com.rojas.dev.tvc.entity.Pedido;

import java.util.List;

public interface PedidoService {

    Pedido agregarPedido(Pedido pedido);

    List<Pedido> obtenerPedidosPorTendero(Integer idTendero);

    List<Pedido> obtenerPedidosPorComerciante(Integer idComerciante);

    Pedido actualizarEstadoPedido(Integer idPedido, String nuevoEstado);

    Pedido agregarPedidoConItems(Pedido pedido);

    List<ItemPedido> obtenerItemsDePedido(Integer idPedido);

    Pedido actualizarEstadoYFechaEntrega(Integer idPedido, Boolean estado, String fechaEntrega);
}
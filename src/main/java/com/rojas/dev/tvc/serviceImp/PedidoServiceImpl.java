package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.ItemPedidoRepository;
import com.rojas.dev.tvc.Repository.PedidoRepository;
import com.rojas.dev.tvc.Repository.ProductoRepository;
import com.rojas.dev.tvc.entity.ItemPedido;
import com.rojas.dev.tvc.entity.Pedido;
import com.rojas.dev.tvc.entity.Producto;
import com.rojas.dev.tvc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido agregarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorTendero(Integer idTendero) {
        return pedidoRepository.findByTendero_IdUsuarioOrderById(idTendero);
    }

    @Override
    public List<Pedido> obtenerPedidosPorComerciante(Integer idComerciante) {
        return pedidoRepository.findPedidosByComerciante(idComerciante);
    }

    @Override
    public Pedido actualizarEstadoPedido(Integer idPedido, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        pedido.setFechaEntrega(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido agregarPedidoConItems(Pedido pedido) {
        // Guardar el pedido
        Pedido nuevoPedido = pedidoRepository.save(pedido);

        // Procesar items
        for (ItemPedido item : pedido.getItems()) {
            item.setPedido(nuevoPedido);

            Producto producto = productoRepository.findById(item.getProducto().getIdProducto())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            // Validar stock
            if (producto.getStock() > item.getCantidad()) {
                // Restar stock
                producto.setStock(producto.getStock() - item.getCantidad());
                productoRepository.save(producto);

                // Guardar el item del pedido
                itemPedidoRepository.save(item);
            }
        }
        return nuevoPedido;
    }

    @Override
    public List<ItemPedido> obtenerItemsDePedido(Integer idPedido) {
        return itemPedidoRepository.findByPedido_IdPedido(idPedido);
    }

    @Override
    public Pedido actualizarEstadoYFechaEntrega(Integer idPedido, Boolean estado, String fechaEntrega) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        pedido.setIdEstadoPedido(estado);
        pedido.setFechaEntrega(fechaEntrega);

        return pedidoRepository.save(pedido);
    }

}

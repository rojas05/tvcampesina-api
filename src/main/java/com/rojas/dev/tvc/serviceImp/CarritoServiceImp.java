package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.CarritoRepository;
import com.rojas.dev.tvc.Repository.ItemCarritoRepository;
import com.rojas.dev.tvc.entity.Carrito;
import com.rojas.dev.tvc.entity.ItemCarrito;
import com.rojas.dev.tvc.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImp implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Override
    public Carrito crearCarrito(Carrito carrito) {
        Integer id = Math.toIntExact(carrito.getTendero().getIdUsuario());
        Optional<Carrito> carritoE = carritoRepository.findByTendero_IdUsuario(id);
        if(carritoE.isPresent()){
            return carritoE.get();
        }else {
            return carritoRepository.save(carrito);
        }
    }

    @Override
    public ItemCarrito agregarItem(ItemCarrito nuevoItem) {
        Integer idCarrito = nuevoItem.getCarrito().getIdCarrito();
        Integer idProducto = nuevoItem.getProducto().getIdProducto();

        List<ItemCarrito> existentes = itemCarritoRepository.findByCarrito_IdCarrito(idCarrito);

        for (ItemCarrito item : existentes) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                item.setCantidad(item.getCantidad() + nuevoItem.getCantidad());
                return itemCarritoRepository.save(item);
            }
        }

        // No existía, se guarda normalmente
        return itemCarritoRepository.save(nuevoItem);
    }

    @Override
    public void eliminarItem(Integer idItem) {
        itemCarritoRepository.deleteById(idItem);
    }

    @Override
    public ItemCarrito actualizarCantidadItem(Integer idItem, Integer nuevaCantidad) {
        ItemCarrito item = itemCarritoRepository.findById(idItem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no encontrado"));

        item.setCantidad(nuevaCantidad);
        return itemCarritoRepository.save(item);
    }

    @Override
    public List<ItemCarrito> obtenerItemsPorTendero(Integer idTendero) {
        Carrito carrito = carritoRepository.findByTendero_IdUsuario(idTendero)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        return itemCarritoRepository.findByCarrito_IdCarrito(carrito.getIdCarrito());
    }

    @Override
    public void eliminarCarrito(Integer idCarrito) {
        carritoRepository.deleteById(idCarrito);
    }
}


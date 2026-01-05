package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.ProductoRepository;
import com.rojas.dev.tvc.entity.Producto;
import com.rojas.dev.tvc.entity.Usuario;
import com.rojas.dev.tvc.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ResponseEntity<?> agregarProducto(Producto producto) {
        try {
            Producto result = productoRepository.save(producto);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> listarProductosPorComerciante(Integer idComerciante) {
        try {
            List<Producto> result = productoRepository.findByComerciante_IdComerciante(idComerciante);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> obtenerProductoPorId(Integer idProducto) {
        try {
            Optional<Producto> result = productoRepository.findById(idProducto);
            return ResponseEntity.ok(result.get());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer idProducto) {
        try {
            productoRepository.deleteById(idProducto);
            return ResponseEntity.ok("ELIMINADO");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> actualizarProducto(Integer idProducto, Producto productoActualizado) {
        try {
            Optional<Producto> productoExistente = productoRepository.findById(idProducto);

            if (productoExistente.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            Producto newProduct = productoExistente.get();

            newProduct.setNombre(productoActualizado.getNombre());
            newProduct.setDescripcion(productoActualizado.getDescripcion());
            newProduct.setCategoria(productoActualizado.getCategoria());
            newProduct.setComerciante(productoActualizado.getComerciante());
            newProduct.setPrecio(productoActualizado.getPrecio());
            newProduct.setStock(productoActualizado.getStock());
            newProduct.setUnidadMedida(productoActualizado.getUnidadMedida());
            newProduct.setImagenUrl(productoActualizado.getImagenUrl());
            newProduct.setFechaRegistro(productoActualizado.getFechaRegistro());

            Producto result = productoRepository.save(newProduct);

            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

}

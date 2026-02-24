package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Pedidos hechos por un tendero, ordenados por estadoINTE
    @Query("SELECT DISTINCT p FROM Pedido p " +
            "WHERE p.tendero.idUsuario = :idTendero " +
            "ORDER BY p.idPedido DESC")
    List<Pedido> findByTendero_IdUsuarioOrderById(Integer idTendero);

    // Pedidos que contienen productos de un comerciante específico (por relaciones)
    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN ItemPedido ip ON ip.pedido.idPedido = p.idPedido " +
            "JOIN Producto prod ON ip.producto.idProducto = prod.idProducto " +
            "WHERE prod.comerciante.idComerciante = :idComerciante " +
            "ORDER BY p.idPedido DESC")
    List<Pedido> findPedidosByComerciante(Integer idComerciante);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.pago = :url WHERE p.idPedido = :id")
    void actualizarImagen(@Param("id") Integer id, @Param("url") String url);
}
package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.Pago;
import com.rojas.dev.tvc.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    // Obtener pagos por comerciante (usando los productos del pedido)
    @Query("""
           SELECT DISTINCT p FROM Pago p
           JOIN p.pedido ped
           JOIN ItemPedido ip ON ip.pedido.idPedido = ped.idPedido
           JOIN Producto prod ON ip.producto.idProducto = prod.idProducto
           WHERE prod.comerciante.idComerciante = :idComerciante
           """)
    List<Pago> findPagosByIdComerciante(Integer idComerciante);

    boolean existsByTransactionId(String transactionId);

    /**
     * Verifica si existe un pago asociado al pedido dado.
     * Spring Data genera automáticamente la consulta:
     * SELECT COUNT(*) > 0 FROM pago WHERE id_pedido = ?
     */
    boolean existsByPedido(Pedido pedido);
}


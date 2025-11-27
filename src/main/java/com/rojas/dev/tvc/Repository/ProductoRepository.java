package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByComerciante_IdComerciante(Integer idComerciante);

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.imagenUrl = :url WHERE p.idProducto = :id")
    void actualizarImagen(@Param("id") Integer id, @Param("url") String url);
}

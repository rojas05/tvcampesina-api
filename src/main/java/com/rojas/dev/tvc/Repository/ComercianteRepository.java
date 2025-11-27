package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.Comerciante;
import com.rojas.dev.tvc.enumClass.EstadoComerciante;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComercianteRepository extends JpaRepository<Comerciante, Integer> {

    Optional<Comerciante> findByUsuario_IdUsuario(Integer idUsuario);

    @Query("SELECT c FROM Comerciante c " +
            "WHERE LOWER(c.categoria.nombre) LIKE LOWER(CONCAT('%', :nombreCategoria, '%')) " +
            "AND LOWER(c.usuario.municipio) = LOWER(:municipio) AND c.estado LIKE :state")
    List<Comerciante> findByCategoriaNombreAndMunicipio(String nombreCategoria, String municipio, EstadoComerciante state);

    @Query("SELECT c FROM Comerciante c " +
            "WHERE c.estado LIKE :state")
    List<Comerciante> findByState(EstadoComerciante state);

    @Modifying
    @Transactional
    @Query("UPDATE Comerciante c SET c.img = :url WHERE c.idComerciante = :id")
    void actualizarImagen(@Param("id") Integer id, @Param("url") String url);

    @Modifying
    @Transactional
    @Query("UPDATE Comerciante c SET c.estado = :estado WHERE c.idComerciante = :id")
    int actualizarEstado(Integer id, EstadoComerciante estado);
}


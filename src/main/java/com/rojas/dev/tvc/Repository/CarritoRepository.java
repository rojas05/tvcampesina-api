package com.rojas.dev.tvc.Repository;


import com.rojas.dev.tvc.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByTendero_IdUsuario(Integer idUsuario);
}

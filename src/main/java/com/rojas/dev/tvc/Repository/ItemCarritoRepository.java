package com.rojas.dev.tvc.Repository;


import com.rojas.dev.tvc.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    List<ItemCarrito> findByCarrito_IdCarrito(Integer idCarrito);
}


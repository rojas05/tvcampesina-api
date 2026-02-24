package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.dto.UpdateComercianteRequest;
import com.rojas.dev.tvc.entity.Comerciante;
import com.rojas.dev.tvc.enumClass.EstadoComerciante;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComercianteService {

    ResponseEntity<?> insertarComerciante(Comerciante comerciante);

    ResponseEntity<?> obtenerPorIdUsuario(Integer idUsuario);

    ResponseEntity<?> obtenerPorId(Integer idUsuario);

    ResponseEntity<?> obtenerPorEstado(EstadoComerciante state);

    ResponseEntity<?> filtrarPorCategoriaYMunicipio(String nombreCategoria, String municipio);

    ResponseEntity<?> actualizarComerciante(Integer idComerciante, Comerciante datosActualizados);

    boolean actualizarEstado(Integer idComerciante, EstadoComerciante nuevoEstado);

    void actualizarComerciante(Integer idComerciante, UpdateComercianteRequest request);
}

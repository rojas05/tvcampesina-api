package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.entity.Pago;

import java.util.List;

public interface PagoService {
    List<Pago> obtenerPagosPorComerciante(Integer idComerciante);
}


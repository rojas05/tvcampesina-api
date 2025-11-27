package com.rojas.dev.tvc.service;

import com.rojas.dev.tvc.entity.CategoriaComerciante;

import java.util.List;

public interface CategoriaComercianteService {

    CategoriaComerciante crear(CategoriaComerciante categoria);

    void eliminar(Integer id);

    CategoriaComerciante actualizar(Integer id, CategoriaComerciante datos);

    List<CategoriaComerciante> listarTodas();
}


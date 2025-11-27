package com.rojas.dev.tvc.serviceImp;


import com.rojas.dev.tvc.Repository.CategoriaComercianteRepository;
import com.rojas.dev.tvc.entity.CategoriaComerciante;
import com.rojas.dev.tvc.service.CategoriaComercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaComercianteServiceImpl implements CategoriaComercianteService {

    @Autowired
    private CategoriaComercianteRepository repository;

    @Override
    public CategoriaComerciante crear(CategoriaComerciante categoria) {
        return repository.save(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public CategoriaComerciante actualizar(Integer id, CategoriaComerciante datos) {
        CategoriaComerciante existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        existente.setNombre(datos.getNombre());
        existente.setImg(datos.getImg());

        return repository.save(existente);
    }

    @Override
    public List<CategoriaComerciante> listarTodas() {
        return repository.findAll();
    }
}


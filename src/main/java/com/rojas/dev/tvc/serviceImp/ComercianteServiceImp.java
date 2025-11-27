package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.ComercianteRepository;
import com.rojas.dev.tvc.entity.Comerciante;
import com.rojas.dev.tvc.enumClass.EstadoComerciante;
import com.rojas.dev.tvc.service.ComercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ComercianteServiceImp implements ComercianteService {

    @Autowired
    private ComercianteRepository comercianteRepository;

    @Override
    public ResponseEntity<?> insertarComerciante(Comerciante comerciante) {
        try {
            Comerciante result = comercianteRepository.save(comerciante);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> obtenerPorIdUsuario(Integer idUsuario) {
        try {
            Optional<Comerciante> result = comercianteRepository.findByUsuario_IdUsuario(idUsuario);
            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no encontrado");
            return ResponseEntity.ok(result.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> obtenerPorId(Integer id) {
        try {
            Optional<Comerciante> result = comercianteRepository.findById(id);
            if (result.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no encontrado");
            return ResponseEntity.ok(result.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> obtenerPorEstado(EstadoComerciante state) {
        try {
            List<Comerciante> result = comercianteRepository.findByState(state);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> filtrarPorCategoriaYMunicipio(String nombreCategoria, String municipio) {
        try {
            List<Comerciante> result = comercianteRepository.findByCategoriaNombreAndMunicipio(nombreCategoria, municipio, EstadoComerciante.ACTIVO);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> actualizarComerciante(Integer idComerciante, Comerciante datos) {
        try {
            Comerciante existente = comercianteRepository.findById(idComerciante)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comerciante no encontrado"));

            existente.setNombreNegocio(datos.getNombreNegocio());
            existente.setDireccion(datos.getDireccion());
            existente.setTelefonoContacto(datos.getTelefonoContacto());
            existente.setEstado(datos.getEstado());
            existente.setFechaRegistro(datos.getFechaRegistro());
            existente.setCategoria(datos.getCategoria());
            existente.setUsuario(datos.getUsuario());
            existente.setImg(datos.getImg());

            Comerciante result = comercianteRepository.save(existente);

            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public boolean actualizarEstado(Integer idComerciante, EstadoComerciante nuevoEstado) {
        int rows = comercianteRepository.actualizarEstado(idComerciante, nuevoEstado);
        return rows > 0;
    }
}


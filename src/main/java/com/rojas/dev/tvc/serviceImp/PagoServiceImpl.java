package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Repository.PagoRepository;
import com.rojas.dev.tvc.entity.Pago;
import com.rojas.dev.tvc.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;


    @Override
    public List<Pago> obtenerPagosPorComerciante(Integer idComerciante) {
        return pagoRepository.findPagosByIdComerciante(idComerciante);
    }
}


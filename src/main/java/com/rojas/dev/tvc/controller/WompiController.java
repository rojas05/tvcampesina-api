package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.Provider.WompiBankProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wompi")
@RequiredArgsConstructor
public class WompiController {

    private final WompiBankProvider wompiBankProvider;

    /**
     * Retorna los bancos disponibles desde Wompi Payouts.
     */
    @GetMapping("/bancos")
    public ResponseEntity<List<WompiBankProvider.BankInfo>> listarBancos() {
        List<WompiBankProvider.BankInfo> bancos = wompiBankProvider.getBanks();
        return ResponseEntity.ok(bancos);
    }
}


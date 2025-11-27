package com.rojas.dev.tvc.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentInitRequest {
    private Integer pedidoId;
    private Integer monto;
    private String moneda;
    private String medio;
    private String referencia;
    private String callbackOk;
    private String callbackKo;
    private List<SplitRule> splitRules; // <-- NUEVO
}


package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.Repository.PaymentLinkRepository;
import com.rojas.dev.tvc.dto.TwoLinksResponse;
import com.rojas.dev.tvc.entity.PaymentLink;
import com.rojas.dev.tvc.serviceImp.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pasarela")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    private final PaymentLinkRepository paymentLinkRepository;

    /**
     @PostMapping("/create-two-links/{pedidoId}")
     public ResponseEntity<TwoLinksResponse> createTwoLinks(
     @PathVariable Long pedidoId,
     @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
     // validar authHeader si tu API lo requiere (no lo reenvíes)
     TwoLinksResponse resp = paymentService.createTwoLinks(pedidoId, "app://tvccomercio/payments/callback");
     return ResponseEntity.ok(resp);
     }
     */


    @GetMapping("/verify/{paymentLinkId}")
    public ResponseEntity<Map<String,String>> verify(@PathVariable String paymentLinkId) {
        Optional<PaymentLink> pl = paymentLinkRepository.findByPaymentLinkId(paymentLinkId);
        if (pl.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("status", "pl.get().getStatus().name()"));
    }
}


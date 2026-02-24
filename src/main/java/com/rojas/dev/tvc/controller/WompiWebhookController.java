package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.serviceImp.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class WompiWebhookController {

    private final PaymentService paymentService;


    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> payload) {
        var data = (Map<String, Object>) payload.get("data");
        var tx = (Map<String, Object>) data.get("transaction");

        String status = (String) tx.get("status");
        String linkId = (String) tx.get("payment_link_id");

        if ("APPROVED".equals(status)) {
            paymentService.markPaymentLinkApproved(linkId);
            //paymentService.processWompiTransaction(txId, status, amountInCents, linkId, payload.toString());
        }

        return ResponseEntity.ok("Pago procesado correctamente");
    }

}



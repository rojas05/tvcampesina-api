package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.Provider.WompiPaymentProvider;
import com.rojas.dev.tvc.Repository.PaymentLinkRepository;
import com.rojas.dev.tvc.Repository.PedidoRepository;
import com.rojas.dev.tvc.entity.Pedido;
import com.rojas.dev.tvc.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PedidoRepository pedidoRepository;
    private final WompiPaymentProvider wompiProvider;
    private final PaymentLinkRepository paymentLinkRepository;
    @Autowired
    PagoService pagoService;

    @PostMapping("/create-link/{pedidoId}/{monto}")
    public ResponseEntity<?> createLink(@PathVariable Integer pedidoId, @PathVariable Integer monto) {
        var result = wompiProvider.createPaymentLink(
                (monto * 100L), // convertir a centavos
                "USER-" + pedidoId,
                "app://tvccomercio/payments/callback"
        );

        var link = com.rojas.dev.tvc.entity.PaymentLink.builder()
                .usuario(pedidoId)
                .referencia("USER-" + pedidoId)
                .paymentLinkId(result.getPaymentLinkId())
                .checkoutUrl(result.getCheckoutUrl())
                .rawResponse(result.getRawResponse())
                .build();

        paymentLinkRepository.save(link);
        System.out.println(link);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comerciante/{comercianteId}")
    public ResponseEntity<?> getPagos(@PathVariable Integer comercianteId) {
        try {
            var response = pagoService.obtenerPagosPorComerciante(comercianteId);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}



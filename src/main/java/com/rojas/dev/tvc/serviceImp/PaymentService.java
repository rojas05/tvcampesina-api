package com.rojas.dev.tvc.serviceImp;

import com.rojas.dev.tvc.Provider.WompiPayoutProvider;
import com.rojas.dev.tvc.Repository.*;
import com.rojas.dev.tvc.entity.*;
import com.rojas.dev.tvc.enumClass.EstadoPago;
import com.rojas.dev.tvc.enumClass.MetodoPago;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

// src/main/java/com/rojas/dev/tvc/service/PaymentService.java
@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    PaymentLinkRepository paymentLinkRepository;

    @Autowired
    UsuarioRepository usuarioRepository ;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    PayoutRepository payoutRepository;

    @Autowired
    WompiPayoutProvider wompiPayoutProvider;

    /**
     * Marca un PaymentLink como aprobado cuando Wompi confirma el pago exitoso.
     * Actualiza también el pedido y crea el registro Pago correspondiente.
     */
    @Transactional
    public void markPaymentLinkApproved(String paymentLinkId) {
        // 1️⃣ Buscar el PaymentLink
        PaymentLink link = paymentLinkRepository.findByPaymentLinkId(paymentLinkId)
                .orElseThrow(() -> new RuntimeException("PaymentLink no encontrado: " + paymentLinkId));

        // 2️⃣ Actualizar estado en el PaymentLink
        //link.setStatus(LinkStatus.APPROVED);
        paymentLinkRepository.save(link);

        // 3️⃣ Buscar el Pedido asociado
        Pedido pedido = pedidoRepository.findById(link.getPedido().getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado para el PaymentLink: " + paymentLinkId));

        // 4️⃣ Actualizar estado del pedido
        pedido.setIdEstadoPedido(true); // o usa un enum si tienes uno (Ej: EstadoPedido.PAGADO)
        pedidoRepository.save(pedido);

        //processWompiTransaction(transactionId, "APPROVED", amountInCents, paymentLinkId, rawBody);
        System.out.println("✅ PaymentLink " + paymentLinkId + " marcado como APROBADO y pedido actualizado.");
    }

    @Transactional
    public void processWompiTransaction(String txId, String status, Integer amountCents, String paymentLinkId, String rawBody) {
        // Idempotencia: si ya se procesó transactionId -> salir
        if (pagoRepository.existsByTransactionId(txId)) {
            // ya procesado
            return;
        }

        // localizar paymentLink -> pedido
        PaymentLink pl = paymentLinkRepository.findByPaymentLinkId(paymentLinkId)
                .orElseThrow(() -> new RuntimeException("PaymentLink not found " + paymentLinkId));

        Pedido pedido = pedidoRepository.findById(pl.getPedido().getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido not found for paymentLink " + paymentLinkId));

        // 1) crear registro Pago (TVC recibió el total)
        Pago pago = Pago.builder()
                .pedido(pedido)
                .monto(amountCents)
                .metodoPago(MetodoPago.TARJETA) // o inferir del transaction
                .estadoPago(EstadoPago.APROBADO) // si status == APPROVED
                .fechaPago(Instant.now().toString())
                .transactionId(txId)
                .rawResponse(rawBody)
                .build();
        pagoRepository.save(pago);

        // 2) actualizar estado del pedido
        pedido.setIdEstadoPedido(true); // o el enum correspondiente
        pedidoRepository.save(pedido);

        // 3) calcular split (90% merchant, 10% tvc)
        int merchantAmount = (int) Math.round(amountCents * 0.99);

        // 4) obtener datos bancarios del comerciante asociado al pedido
        Comerciante comerciante = pedido.getItems().get(0).getProducto().getComerciante(); // tu modelo puede variar
        if (comerciante == null || comerciante.getAccountNumber() == null) {
            // No hay cuenta bancaria -> marcar payout failed y dejar pendiente
            Payout payoutFail = Payout.builder()
                    .pedidoId(pedido.getIdPedido())
                    .comercianteId(null)
                    .amount(merchantAmount)
                    .currency("COP")
                    .status("FAILED")
                    .rawResponse("No bank info")
                    .createdAt(Instant.now().toString())
                    .build();
            payoutRepository.save(payoutFail);
            return;
        }

        Map<String,String> bankInfo = Map.of(
                "bank_code", comerciante.getBankCode(),
                "account_number", comerciante.getAccountNumber(),
                "account_owner_name", comerciante.getAccountOwnerName(),
                "account_owner_document", comerciante.getAccountDocument(),
                "account_owner_document_type", "CC",
                "account_type", comerciante.getAccountType() == null ? "checking" : comerciante.getAccountType()
        );

        // 5) crear registro de payout PENDING
        Payout payout = Payout.builder()
                .pedidoId(pedido.getIdPedido())
                .comercianteId(comerciante.getIdComerciante())
                .amount(merchantAmount)
                .currency("COP")
                .status("PENDING")
                .createdAt(Instant.now().toString())
                .build();
        payoutRepository.save(payout);

        // 6) ejecutar payout hacia comerciante (Wompi payouts)
        try {
            var userEmail = comerciante.getUsuario().getCorreo();
            WompiPayoutProvider.PayoutResult result = wompiPayoutProvider.createPayoutToMerchant(merchantAmount, bankInfo, "PAYOUT-PED-" + pedido.getIdPedido(), userEmail);
            // actualizar payout
            payout.setPayoutId(result.getPayoutId());
            payout.setStatus(result.getStatus());
            payout.setRawResponse(result.getRawResponse());
            payoutRepository.save(payout);

            // 7) crear registro para TVC (si quieres guardar el 10%)
            // Puedes decidir si registras un Pago aparte para tvcAmount (no es necesario si pago ya guarda total)
        } catch (Exception ex) {
            // manejar fallo: registrar razón, dejar en estado FAILED y encolar reintento
            payout.setStatus("FAILED");
            payout.setReason(ex.getMessage());
            payoutRepository.save(payout);
            // opcional: enviar notificación, encolar para retry
        }
    }
}

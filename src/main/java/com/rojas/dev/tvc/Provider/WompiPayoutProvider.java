package com.rojas.dev.tvc.Provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WompiPayoutProvider {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Configuración inyectada desde application.yml
    private final String apiKey;
    private final String userPrincipalId;
    private final String tvcAccountId; // Cuenta origen (TVC)

    public WompiPayoutProvider(
            @Value("${wompi.payouts.baseUrl}") String baseUrl,
            @Value("${wompi.payouts.apiKey}") String apiKey,
            @Value("${wompi.payouts.userPrincipalId}") String userPrincipalId,
            @Value("${wompi.payouts.accountId}") String tvcAccountId
    ) {
        this.apiKey = apiKey;
        this.userPrincipalId = userPrincipalId;
        this.tvcAccountId = tvcAccountId;
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    /**
     * Envía un payout desde la cuenta TVC hacia el comerciante o distribuidor.
     *
     * @param amount        Monto del payout (COP, no centavos)
     * @param merchantInfo  Información bancaria del comerciante o distribuidor destino:
     *                      bankId, accountNumber, accountType, legalIdType, legalId, name, email
     * @param reference     Referencia única interna (ej: "PAYOUT-PED-123")
     */
    public PayoutResult createPayoutToMerchant(Integer amount, Map<String, String> merchantInfo, String reference, String email) {

        // Construir transacción de destino (comerciante/distribuidor)
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("legalIdType", "CC");
        transaction.put("legalId", merchantInfo.get("account_owner_document"));
        transaction.put("bankId", merchantInfo.get("bank_code")); // UUID del banco
        transaction.put("accountType", merchantInfo.getOrDefault("accountType", "AHORROS"));
        transaction.put("accountNumber", merchantInfo.get("account_number"));
        transaction.put("name", merchantInfo.get("account_owner_name"));
        transaction.put("email", email);
        transaction.put("amount", amount);
        transaction.put("reference", reference);

        // Construir cuerpo principal del payout
        Map<String, Object> body = new HashMap<>();
        body.put("reference", reference);
        body.put("accountId", tvcAccountId);       // cuenta de TVC (origen del dinero)
        body.put("paymentType", "PAYROLL");        // tipo de pago según Wompi
        body.put("transactionStatus", "APPROVED");  // estado inicial
        body.put("transactions", List.of(transaction));

        log.info("➡️ Enviando payout a comerciante: {}", merchantInfo);
        log.debug("Body enviado a Wompi Payouts: {}", body);

        // Ejecutar POST al endpoint de payouts
        Mono<String> mono = webClient.post()
                .uri("/payouts")
                .headers(h -> {
                    h.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    h.set("x-api-key", apiKey);
                    h.set("user-principal-id", userPrincipalId);
                })
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), resp ->
                        resp.bodyToMono(String.class)
                                .flatMap(b -> Mono.error(new RuntimeException("Wompi Payout error: " + resp.statusCode().value() + " body=" + b)))
                )
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30));

        String raw = mono.block();

        // Procesar respuesta
        try {
            JsonNode root = objectMapper.readTree(raw).path("data");
            String payoutId = root.path("id").asText(null);
            String status = root.path("status").asText("PENDING");

            log.info("✅ Payout creado con éxito: id={}, status={}", payoutId, status);

            return PayoutResult.builder()
                    .payoutId(payoutId)
                    .status(status)
                    .rawResponse(raw)
                    .build();

        } catch (Exception e) {
            log.error("❌ Error al parsear respuesta del payout: {}", raw);
            throw new RuntimeException("Error parsing payout response", e);
        }
    }

    @Data
    @Builder
    public static class PayoutResult {
        private String payoutId;
        private String status;
        private String rawResponse;
    }
}

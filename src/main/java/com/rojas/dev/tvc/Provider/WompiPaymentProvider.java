package com.rojas.dev.tvc.Provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class WompiPaymentProvider {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String privateKey;
    private final String checkoutBase;

    public WompiPaymentProvider(
            @Value("${wompi.baseUrl}") String baseUrl,
            @Value("${wompi.privateKey}") String privateKey,
            @Value("${wompi.checkoutBase}") String checkoutBase
    ) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.privateKey = privateKey;
        this.checkoutBase = checkoutBase;
    }

    public PaymentLinkResult createPaymentLink(long amountCents, String reference, String redirectUrl) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Pago pedido " + reference);
        body.put("description", "Pago correspondiente al pedido " + reference);
        body.put("amount_in_cents", amountCents);
        body.put("currency", "COP");
        body.put("single_use", true);
        body.put("collect_shipping", false);
        body.put("redirect_url", "https://sandbox-checkout.wompi.co/success");

        Mono<String> responseMono = webClient.post()
                .uri("/payment_links")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + privateKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .onStatus(s -> !s.is2xxSuccessful(),
                        resp -> resp.bodyToMono(String.class).flatMap(b -> Mono.error(new RuntimeException("Wompi error: " + b))))
                .bodyToMono(String.class);

        String response = responseMono.block();

        try {
            JsonNode json = objectMapper.readTree(response).path("data");
            String id = json.path("id").asText();
            String url = checkoutBase + "/" + id;

            return new PaymentLinkResult(id, url, response);

        } catch (Exception e) {
            throw new RuntimeException("Error parsing Wompi response: " + response, e);
        }
    }

    @Data
    @Builder
    public static class PaymentLinkResult {
        private final String paymentLinkId;
        private final String checkoutUrl;
        private final String rawResponse;
    }
}

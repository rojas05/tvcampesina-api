package com.rojas.dev.tvc.Provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class WompiBankProvider {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String payoutsBaseUrl;
    private final String apiKey;
    private final String userPrincipalId;

    public WompiBankProvider(
            @Value("${wompi.payouts.baseUrl}") String payoutsBaseUrl,
            @Value("${wompi.payouts.apiKey}") String apiKey,
            @Value("${wompi.payouts.userPrincipalId}") String userPrincipalId
    ) {
        this.payoutsBaseUrl = payoutsBaseUrl;
        this.apiKey = apiKey;
        this.userPrincipalId = userPrincipalId;
        this.webClient = WebClient.builder().baseUrl(payoutsBaseUrl).build();
    }

    /**
     * Consulta el listado de bancos desde Wompi Payouts.
     */
    public List<BankInfo> getBanks() {
        try {
            Mono<String> mono = webClient.get()
                    .uri("/banks")
                    .headers(h -> {
                        h.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        h.set("x-api-key", apiKey);
                        h.set("user-principal-id", userPrincipalId);
                    })
                    .retrieve()
                    .onStatus(s -> !s.is2xxSuccessful(), resp ->
                            resp.bodyToMono(String.class)
                                    .flatMap(b -> Mono.error(new RuntimeException("Wompi error: " + resp.statusCode().value() + " body=" + b)))
                    )
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(30));

            String response = mono.block();

            JsonNode root = objectMapper.readTree(response).path("data");
            List<BankInfo> banks = new ArrayList<>();

            for (JsonNode node : root) {
                BankInfo bank = BankInfo.builder()
                        .id(node.path("id").asText())
                        .name(node.path("name").asText())
                        .code(node.path("code").asText())
                        .type(node.path("type").asText())
                        .build();
                banks.add(bank);
            }

            return banks;

        } catch (Exception e) {
            throw new RuntimeException("Error consultando bancos desde Wompi: " + e.getMessage(), e);
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BankInfo {
        private String id;
        private String name;
        private String code;
        private String type;
    }
}


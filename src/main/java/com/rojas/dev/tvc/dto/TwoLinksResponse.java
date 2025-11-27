package com.rojas.dev.tvc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwoLinksResponse {
    private Long pedidoId;
    private String merchantCheckoutUrl;
    private String merchantPaymentLinkId;
    private String tvcCheckoutUrl;
    private String tvcPaymentLinkId;
}


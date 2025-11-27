package com.rojas.dev.tvc.Provider;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentLinkResult {
    private String paymentLinkId;
    private String checkoutUrl;
    private String rawResponse; // optional
}


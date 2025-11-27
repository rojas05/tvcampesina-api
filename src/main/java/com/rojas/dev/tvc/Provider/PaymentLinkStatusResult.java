package com.rojas.dev.tvc.Provider;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentLinkStatusResult {
    private String status; // e.g. APPROVED, PENDING, FAILED
    private String rawResponse;
}


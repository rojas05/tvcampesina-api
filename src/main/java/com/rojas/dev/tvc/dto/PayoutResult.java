package com.rojas.dev.tvc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayoutResult {
    private String payoutId;
    private String status;
    private String rawResponse;
}


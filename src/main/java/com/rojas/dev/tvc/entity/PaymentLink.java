package com.rojas.dev.tvc.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referencia;
    private String paymentLinkId;
    private String checkoutUrl;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String rawResponse;
    //private String status;

    private Integer usuario;
}

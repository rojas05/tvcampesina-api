package com.rojas.dev.tvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayout;

    private String payoutId; // id devolvido por Wompi para el payout (si aplica)
    private Integer pedidoId;
    private Integer comercianteId;
    private Integer amount; // en centavos
    private String currency; // "COP"
    private String status; // PENDING, SUCCESS, FAILED
    private String rawResponse; // LONGTEXT

    @Lob
    @Column(columnDefinition = "TEXT")
    private String reason; // en caso de fallo
    private String createdAt;
}


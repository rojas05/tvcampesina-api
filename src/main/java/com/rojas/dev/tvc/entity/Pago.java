package com.rojas.dev.tvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rojas.dev.tvc.enumClass.EstadoPago;
import com.rojas.dev.tvc.enumClass.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    private Integer monto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String rawResponse;

    private String fechaPago;
}



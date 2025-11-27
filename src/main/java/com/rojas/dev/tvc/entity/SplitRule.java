package com.rojas.dev.tvc.entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SplitRule {
    private String destinationAccount; // Cuenta Wompi del receptor
    private int percentage;            // Porcentaje entero (ej: 10 = 10%)
    private int amountInCents;         // O monto fijo (una de las dos opciones)
}
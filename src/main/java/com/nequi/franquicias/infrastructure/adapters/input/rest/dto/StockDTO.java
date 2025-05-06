package com.nequi.franquicias.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int nuevoStock;
}
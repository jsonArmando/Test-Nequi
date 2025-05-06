package com.nequi.franquicias.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private String id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}
package com.nequi.franquicias.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private String id;
    
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombre;
    
    @Builder.Default
    private List<ProductoDTO> productos = new ArrayList<>();
}

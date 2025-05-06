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
public class FranquiciaDTO {
    private String id;
    
    @NotBlank(message = "El nombre de la franquicia es obligatorio")
    private String nombre;
    
    @Builder.Default
    private List<SucursalDTO> sucursales = new ArrayList<>();
}

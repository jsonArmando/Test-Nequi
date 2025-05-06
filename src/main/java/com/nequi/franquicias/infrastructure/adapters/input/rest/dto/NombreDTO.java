package com.nequi.franquicias.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NombreDTO {
    @NotBlank(message = "El nuevo nombre es obligatorio")
    private String nuevoNombre;
}
package com.nequi.franquicias.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalEntity {
    private String id;
    private String nombre;
    
    @Builder.Default
    private List<ProductoEntity> productos = new ArrayList<>();
}
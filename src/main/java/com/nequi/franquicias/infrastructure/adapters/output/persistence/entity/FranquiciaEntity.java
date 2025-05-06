package com.nequi.franquicias.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "franquicias")
public class FranquiciaEntity {
    @Id
    private String id;
    private String nombre;
    
    @Builder.Default
    private List<SucursalEntity> sucursales = new ArrayList<>();
}
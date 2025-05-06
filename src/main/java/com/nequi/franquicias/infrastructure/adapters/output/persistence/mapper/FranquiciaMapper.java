package com.nequi.franquicias.infrastructure.adapters.output.persistence.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.entity.FranquiciaEntity;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.entity.ProductoEntity;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.entity.SucursalEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FranquiciaMapper {

    public Franquicia toDomain(FranquiciaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Franquicia.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .sucursales(entity.getSucursales().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    public FranquiciaEntity toEntity(Franquicia domain) {
        if (domain == null) {
            return null;
        }
        
        return FranquiciaEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .sucursales(domain.getSucursales().stream()
                        .map(this::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private Sucursal toDomain(SucursalEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Sucursal.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .productos(entity.getProductos().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    private SucursalEntity toEntity(Sucursal domain) {
        if (domain == null) {
            return null;
        }
        
        return SucursalEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .productos(domain.getProductos().stream()
                        .map(this::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private Producto toDomain(ProductoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Producto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .stock(entity.getStock())
                .build();
    }

    private ProductoEntity toEntity(Producto domain) {
        if (domain == null) {
            return null;
        }
        
        return ProductoEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .stock(domain.getStock())
                .build();
    }
}
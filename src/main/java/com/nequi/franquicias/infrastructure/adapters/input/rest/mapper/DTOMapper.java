package com.nequi.franquicias.infrastructure.adapters.input.rest.mapper;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapters.input.rest.dto.FranquiciaDTO;
import com.nequi.franquicias.infrastructure.adapters.input.rest.dto.ProductoDTO;
import com.nequi.franquicias.infrastructure.adapters.input.rest.dto.SucursalDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DTOMapper {

    public Franquicia toDomain(FranquiciaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Franquicia.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .sucursales(dto.getSucursales().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public FranquiciaDTO toDTO(Franquicia domain) {
        if (domain == null) {
            return null;
        }
        
        return FranquiciaDTO.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .sucursales(domain.getSucursales().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public Sucursal toDomain(SucursalDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Sucursal.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .productos(dto.getProductos().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public SucursalDTO toDTO(Sucursal domain) {
        if (domain == null) {
            return null;
        }
        
        return SucursalDTO.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .productos(domain.getProductos().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public Producto toDomain(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Producto.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .stock(dto.getStock())
                .build();
    }
    
    public ProductoDTO toDTO(Producto domain) {
        if (domain == null) {
            return null;
        }
        
        return ProductoDTO.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .stock(domain.getStock())
                .build();
    }
}
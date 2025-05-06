package com.nequi.franquicias.infrastructure.adapters.input.rest;

import com.nequi.franquicias.application.ports.input.FranquiciaReactiveService;
import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.Sucursal;
import com.nequi.franquicias.infrastructure.adapters.input.rest.dto.*;
import com.nequi.franquicias.infrastructure.adapters.input.rest.mapper.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reactive/franquicias")
@RequiredArgsConstructor
public class FranquiciaReactiveController {

    private final FranquiciaReactiveService franquiciaService;
    private final DTOMapper dtoMapper;

    // Endpoints para Franquicia
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaDTO> crearFranquicia(@Valid @RequestBody FranquiciaDTO franquiciaDTO) {
        Franquicia franquicia = dtoMapper.toDomain(franquiciaDTO);
        return franquiciaService.crearFranquicia(franquicia)
                .map(dtoMapper::toDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<FranquiciaDTO> obtenerTodasFranquicias() {
        return franquiciaService.obtenerTodasFranquicias()
                .map(dtoMapper::toDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<FranquiciaDTO> obtenerFranquiciaPorId(@PathVariable String id) {
        return franquiciaService.obtenerFranquiciaPorId(id)
                .map(dtoMapper::toDTO);
    }

    @PutMapping("/{id}")
    public Mono<FranquiciaDTO> actualizarFranquicia(
            @PathVariable String id,
            @Valid @RequestBody FranquiciaDTO franquiciaDTO) {
        Franquicia franquicia = dtoMapper.toDomain(franquiciaDTO);
        return franquiciaService.actualizarFranquicia(id, franquicia)
                .map(dtoMapper::toDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarFranquicia(@PathVariable String id) {
        return franquiciaService.eliminarFranquicia(id);
    }

    // Endpoints para actualizar nombres
    @PatchMapping("/{id}/nombre")
    public Mono<FranquiciaDTO> actualizarNombreFranquicia(
            @PathVariable String id,
            @Valid @RequestBody NombreDTO nombreDTO) {
        return franquiciaService.actualizarNombreFranquicia(id, nombreDTO.getNuevoNombre())
                .map(dtoMapper::toDTO);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public Mono<FranquiciaDTO> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody NombreDTO nombreDTO) {
        return franquiciaService.actualizarNombreSucursal(franquiciaId, sucursalId, nombreDTO.getNuevoNombre())
                .map(dtoMapper::toDTO);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre")
    public Mono<FranquiciaDTO> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody NombreDTO nombreDTO) {
        return franquiciaService.actualizarNombreProducto(franquiciaId, sucursalId, productoId, nombreDTO.getNuevoNombre())
                .map(dtoMapper::toDTO);
    }

    // Endpoints para Sucursal
    @PostMapping("/{franquiciaId}/sucursales")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaDTO> agregarSucursalAFranquicia(
            @PathVariable String franquiciaId,
            @Valid @RequestBody SucursalDTO sucursalDTO) {
        Sucursal sucursal = dtoMapper.toDomain(sucursalDTO);
        return franquiciaService.agregarSucursalAFranquicia(franquiciaId, sucursal)
                .map(dtoMapper::toDTO);
    }

    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}")
    public Mono<FranquiciaDTO> eliminarSucursalDeFranquicia(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId) {
        return franquiciaService.eliminarSucursalDeFranquicia(franquiciaId, sucursalId)
                .map(dtoMapper::toDTO);
    }

    // Endpoints para Producto
    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaDTO> agregarProductoASucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = dtoMapper.toDomain(productoDTO);
        return franquiciaService.agregarProductoASucursal(franquiciaId, sucursalId, producto)
                .map(dtoMapper::toDTO);
    }

    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public Mono<FranquiciaDTO> eliminarProductoDeSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId) {
        return franquiciaService.eliminarProductoDeSucursal(franquiciaId, sucursalId, productoId)
                .map(dtoMapper::toDTO);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    public Mono<FranquiciaDTO> actualizarStockProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody StockDTO stockDTO) {
        return franquiciaService.actualizarStockProducto(franquiciaId, sucursalId, productoId, stockDTO.getNuevoStock())
                .map(dtoMapper::toDTO);
    }

    // Endpoint especial
    @GetMapping("/{franquiciaId}/productos/mas-stock")
    public Flux<ProductoDTO> obtenerProductoConMasStockPorSucursal(@PathVariable String franquiciaId) {
        return franquiciaService.obtenerProductoConMasStockPorSucursal(franquiciaId)
                .map(dtoMapper::toDTO);
    }
}
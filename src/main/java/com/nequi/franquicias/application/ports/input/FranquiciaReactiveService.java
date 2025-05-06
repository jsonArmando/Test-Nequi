package com.nequi.franquicias.application.ports.input;

import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaReactiveService {
    // Franquicia operations
    Mono<Franquicia> crearFranquicia(Franquicia franquicia);
    Flux<Franquicia> obtenerTodasFranquicias();
    Mono<Franquicia> obtenerFranquiciaPorId(String id);
    Mono<Franquicia> actualizarFranquicia(String id, Franquicia franquicia);
    Mono<Void> eliminarFranquicia(String id);
    
    // Sucursal operations
    Mono<Franquicia> agregarSucursalAFranquicia(String franquiciaId, Sucursal sucursal);
    Mono<Franquicia> eliminarSucursalDeFranquicia(String franquiciaId, String sucursalId);
    
    // Producto operations
    Mono<Franquicia> agregarProductoASucursal(String franquiciaId, String sucursalId, Producto producto);
    Mono<Franquicia> eliminarProductoDeSucursal(String franquiciaId, String sucursalId, String productoId);
    Mono<Franquicia> actualizarStockProducto(String franquiciaId, String sucursalId, String productoId, int nuevoStock);
    
    // Consulta especial
    Flux<Producto> obtenerProductoConMasStockPorSucursal(String franquiciaId);
    
    // Operaciones adicionales para actualizar nombres
    Mono<Franquicia> actualizarNombreFranquicia(String id, String nuevoNombre);
    Mono<Franquicia> actualizarNombreSucursal(String franquiciaId, String sucursalId, String nuevoNombre);
    Mono<Franquicia> actualizarNombreProducto(String franquiciaId, String sucursalId, String productoId, String nuevoNombre);
}
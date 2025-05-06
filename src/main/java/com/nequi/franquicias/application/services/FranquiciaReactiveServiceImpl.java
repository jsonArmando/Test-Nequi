package com.nequi.franquicias.application.services;

import com.nequi.franquicias.application.ports.input.FranquiciaReactiveService;
import com.nequi.franquicias.application.ports.output.FranquiciaReactiveRepository;
import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.domain.model.Producto;
import com.nequi.franquicias.domain.model.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranquiciaReactiveServiceImpl implements FranquiciaReactiveService {

    private final FranquiciaReactiveRepository franquiciaRepository;

    @Override
    public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }

    @Override
    public Flux<Franquicia> obtenerTodasFranquicias() {
        return franquiciaRepository.findAll();
    }

    @Override
    public Mono<Franquicia> obtenerFranquiciaPorId(String id) {
        return franquiciaRepository.findById(id);
    }

    @Override
    public Mono<Franquicia> actualizarFranquicia(String id, Franquicia franquiciaActualizada) {
        return franquiciaRepository.findById(id)
                .flatMap(franquiciaExistente -> {
                    franquiciaActualizada.setId(id);
                    franquiciaActualizada.setSucursales(franquiciaExistente.getSucursales());
                    return franquiciaRepository.save(franquiciaActualizada);
                });
    }

    @Override
    public Mono<Void> eliminarFranquicia(String id) {
        return franquiciaRepository.deleteById(id);
    }

    @Override
    public Mono<Franquicia> agregarSucursalAFranquicia(String franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Franquicia no encontrada con ID: " + franquiciaId)))
                .flatMap(franquicia -> {
                    sucursal.setId(UUID.randomUUID().toString());
                    franquicia.getSucursales().add(sucursal);
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> eliminarSucursalDeFranquicia(String franquiciaId, String sucursalId) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    franquicia.setSucursales(
                            franquicia.getSucursales().stream()
                                    .filter(s -> !s.getId().equals(sucursalId))
                                    .collect(Collectors.toList())
                    );
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> agregarProductoASucursal(String franquiciaId, String sucursalId, Producto producto) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Franquicia no encontrada con ID: " + franquiciaId)))
                .flatMap(franquicia -> {
                    Optional<Sucursal> sucursalOpt = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst();
                    
                    if (sucursalOpt.isEmpty()) {
                        return Mono.error(new NoSuchElementException("Sucursal no encontrada con ID: " + sucursalId));
                    }
                    
                    Sucursal sucursal = sucursalOpt.get();
                    producto.setId(UUID.randomUUID().toString());
                    sucursal.getProductos().add(producto);
                    
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> eliminarProductoDeSucursal(String franquiciaId, String sucursalId, String productoId) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .ifPresent(sucursal -> {
                                sucursal.setProductos(
                                        sucursal.getProductos().stream()
                                                .filter(p -> !p.getId().equals(productoId))
                                                .collect(Collectors.toList())
                                );
                            });
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> actualizarStockProducto(String franquiciaId, String sucursalId, String productoId, int nuevoStock) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    boolean actualizado = false;
                    
                    for (Sucursal sucursal : franquicia.getSucursales()) {
                        if (sucursal.getId().equals(sucursalId)) {
                            for (Producto producto : sucursal.getProductos()) {
                                if (producto.getId().equals(productoId)) {
                                    producto.setStock(nuevoStock);
                                    actualizado = true;
                                    break;
                                }
                            }
                            if (actualizado) {
                                break;
                            }
                        }
                    }
                    
                    if (actualizado) {
                        return franquiciaRepository.save(franquicia);
                    } else {
                        return Mono.error(new NoSuchElementException("No se pudo actualizar el stock"));
                    }
                });
    }

    @Override
    public Flux<Producto> obtenerProductoConMasStockPorSucursal(String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMapMany(franquicia -> {
                    List<Producto> productosConMasStock = new ArrayList<>();
                    
                    // Usando programación funcional para encontrar productos con más stock
                    franquicia.getSucursales().forEach(sucursal -> 
                        sucursal.getProductos().stream()
                                .max(Comparator.comparing(Producto::getStock))
                                .ifPresent(productosConMasStock::add)
                    );
                    
                    return Flux.fromIterable(productosConMasStock);
                });
    }

    @Override
    public Mono<Franquicia> actualizarNombreFranquicia(String id, String nuevoNombre) {
        return franquiciaRepository.findById(id)
                .flatMap(franquicia -> {
                    franquicia.setNombre(nuevoNombre);
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> actualizarNombreSucursal(String franquiciaId, String sucursalId, String nuevoNombre) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    boolean actualizado = false;
                    
                    for (Sucursal sucursal : franquicia.getSucursales()) {
                        if (sucursal.getId().equals(sucursalId)) {
                            sucursal.setNombre(nuevoNombre);
                            actualizado = true;
                            break;
                        }
                    }
                    
                    if (actualizado) {
                        return franquiciaRepository.save(franquicia);
                    } else {
                        return Mono.error(new NoSuchElementException("Sucursal no encontrada con ID: " + sucursalId));
                    }
                });
    }

    @Override
    public Mono<Franquicia> actualizarNombreProducto(String franquiciaId, String sucursalId, String productoId, String nuevoNombre) {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    boolean actualizado = false;
                    
                    for (Sucursal sucursal : franquicia.getSucursales()) {
                        if (sucursal.getId().equals(sucursalId)) {
                            for (Producto producto : sucursal.getProductos()) {
                                if (producto.getId().equals(productoId)) {
                                    producto.setNombre(nuevoNombre);
                                    actualizado = true;
                                    break;
                                }
                            }
                            if (actualizado) {
                                break;
                            }
                        }
                    }
                    
                    if (actualizado) {
                        return franquiciaRepository.save(franquicia);
                    } else {
                        return Mono.error(new NoSuchElementException("No se pudo actualizar el nombre del producto"));
                    }
                });
    }
}
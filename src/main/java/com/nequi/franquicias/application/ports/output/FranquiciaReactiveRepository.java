package com.nequi.franquicias.application.ports.output;

import com.nequi.franquicias.domain.model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaReactiveRepository  {
    Mono<Franquicia> save(Franquicia franquicia);
    Flux<Franquicia> findAll();
    Mono<Franquicia> findById(String id);
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}
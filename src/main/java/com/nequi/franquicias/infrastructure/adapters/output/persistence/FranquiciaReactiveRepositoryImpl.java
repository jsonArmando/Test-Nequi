package com.nequi.franquicias.infrastructure.adapters.output.persistence;

import com.nequi.franquicias.application.ports.output.FranquiciaReactiveRepository;
import com.nequi.franquicias.domain.model.Franquicia;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.entity.FranquiciaEntity;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.mapper.FranquiciaMapper;
import com.nequi.franquicias.infrastructure.adapters.output.persistence.repository.MongoFranquiciaReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranquiciaReactiveRepositoryImpl implements FranquiciaReactiveRepository {

    private final MongoFranquiciaReactiveRepository mongoRepository;
    private final FranquiciaMapper mapper;

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        FranquiciaEntity franquiciaEntity = mapper.toEntity(franquicia);
        return mongoRepository.save(franquiciaEntity)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franquicia> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franquicia> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return mongoRepository.existsById(id);
    }
}
package com.nequi.franquicias.infrastructure.adapters.output.persistence.repository;

import com.nequi.franquicias.infrastructure.adapters.output.persistence.entity.FranquiciaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFranquiciaReactiveRepository extends ReactiveMongoRepository<FranquiciaEntity, String> {
}
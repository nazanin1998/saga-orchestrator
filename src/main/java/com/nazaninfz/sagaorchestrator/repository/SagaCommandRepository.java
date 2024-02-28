package com.nazaninfz.sagaorchestrator.repository;

import com.nazaninfz.sagaorchestrator.core.entity.SagaCommandEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SagaCommandRepository extends MongoRepository<SagaCommandEntity, String> {
}

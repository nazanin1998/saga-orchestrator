package com.nazaninfz.sagaorchestrator.repository;

import com.nazaninfz.sagaorchestrator.core.entity.SagaSequenceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SagaSequenceRepository extends MongoRepository<SagaSequenceEntity, String> {
}

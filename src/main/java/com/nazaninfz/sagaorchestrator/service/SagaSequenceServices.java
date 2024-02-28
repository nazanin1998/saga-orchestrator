package com.nazaninfz.sagaorchestrator.service;

import com.nazaninfz.sagaorchestrator.core.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.core.exception.mongo.SaveSequenceException;
import com.nazaninfz.sagaorchestrator.repository.SagaSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaSequenceServices {
    private final SagaSequenceRepository sequenceRepository;

    public SagaSequenceEntity saveSequence(SagaSequenceEntity entity) {

        try {
            return sequenceRepository.save(entity);
        } catch (Exception e) {
            log.error("exception on save saga sequence", e);
            throw new SaveSequenceException(e);
        }
    }
}

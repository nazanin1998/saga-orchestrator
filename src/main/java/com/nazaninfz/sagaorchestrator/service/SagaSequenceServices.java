package com.nazaninfz.sagaorchestrator.service;

import com.nazaninfz.sagaorchestrator.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.exception.mongo.SaveSequenceException;
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

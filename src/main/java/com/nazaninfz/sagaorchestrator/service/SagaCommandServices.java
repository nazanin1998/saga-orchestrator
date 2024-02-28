package com.nazaninfz.sagaorchestrator.service;

import com.nazaninfz.sagaorchestrator.core.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.core.exception.mongo.SaveCommandException;
import com.nazaninfz.sagaorchestrator.repository.SagaCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaCommandServices {
    private final SagaCommandRepository commandRepository;

    public SagaCommandEntity saveCommand(
            SagaCommandEntity entity
    ) {
        try {
            return commandRepository.save(entity);
        } catch (Exception e) {
            log.error("exception on save saga command", e);
            throw new SaveCommandException(e);
        }
    }
}

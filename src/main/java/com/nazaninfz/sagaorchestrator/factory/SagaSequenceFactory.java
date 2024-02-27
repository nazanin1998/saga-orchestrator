package com.nazaninfz.sagaorchestrator.factory;

import com.nazaninfz.sagaorchestrator.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.enums.SequenceStatus;
import com.nazaninfz.sagaorchestrator.mapper.SequenceEntityMapper;
import com.nazaninfz.sagaorchestrator.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.model.SagaSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SagaSequenceFactory {

    private final SequenceEntityMapper sequenceEntityMapper;

    public SagaSequenceEntity createSequence(SagaSequence sequence) {
        return sequenceEntityMapper.modelToDto(sequence)
                .setSequenceId(SagaBaseCommand.generateId())
                .setSequenceStatus(SequenceStatus.INITIALIZED);
    }
}

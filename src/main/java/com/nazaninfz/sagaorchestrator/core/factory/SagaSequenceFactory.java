package com.nazaninfz.sagaorchestrator.core.factory;

import com.nazaninfz.sagaorchestrator.core.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.core.enums.SequenceStatus;
import com.nazaninfz.sagaorchestrator.core.mapper.SequenceEntityMapper;
import com.nazaninfz.sagaorchestrator.core.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.core.model.SagaSequence;
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

package com.nazaninfz.sagaorchestrator.core.mapper;

import com.nazaninfz.sagaorchestrator.core.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.core.model.SagaSequence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SequenceEntityMapper extends GeneralMapper<SagaSequence, SagaSequenceEntity> {
}

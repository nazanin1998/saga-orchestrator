package com.nazaninfz.sagaorchestrator.mapper;

import com.nazaninfz.sagaorchestrator.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.model.SagaSequence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SequenceEntityMapper extends GeneralMapper<SagaSequence, SagaSequenceEntity> {
}

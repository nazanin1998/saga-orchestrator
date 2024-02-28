package com.nazaninfz.sagaorchestrator.core.factory;

import com.nazaninfz.sagaorchestrator.core.enums.SagaCommandStepStatus;
import com.nazaninfz.sagaorchestrator.core.enums.SagaCommandStepType;
import com.nazaninfz.sagaorchestrator.core.model.SagaCommandStepHistory;
import com.nazaninfz.sagaorchestrator.core.model.SagaIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class SagaCommandStepFactory {

    public static SagaCommandStepHistory success(SagaIdentifier identifier, SagaCommandStepType type) {
        return from(identifier, type)
                .setStepStatus(SagaCommandStepStatus.SUCCEED);
    }

    public static SagaCommandStepHistory failure(SagaIdentifier identifier, SagaCommandStepType type) {
        return from(identifier, type)
                .setStepStatus(SagaCommandStepStatus.FAILED);
    }

    public static SagaCommandStepHistory failure(SagaIdentifier identifier, SagaCommandStepType type, Throwable e) {
        return failure(identifier, type)
                .setDetails(ExceptionUtils.getStackTrace(e));
    }

    public static SagaCommandStepHistory from(SagaIdentifier identifier, SagaCommandStepType type) {
        return (SagaCommandStepHistory) new SagaCommandStepHistory()
                .setStepType(type)
                .setId(identifier.getId())
                .setTitle(identifier.getTitle());
    }
}

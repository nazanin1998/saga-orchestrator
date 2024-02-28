package com.nazaninfz.sagaorchestrator.core.exception;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_COMMAND_CONDITION_CHECKING_EXCEPTION;

public class ConditionCheckException extends SagaGeneralException {
    public ConditionCheckException(Throwable e) {
        super(SAGA_COMMAND_CONDITION_CHECKING_EXCEPTION, e);
    }
}

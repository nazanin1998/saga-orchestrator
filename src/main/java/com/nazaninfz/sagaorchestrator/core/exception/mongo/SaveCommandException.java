package com.nazaninfz.sagaorchestrator.core.exception.mongo;

import com.nazaninfz.sagaorchestrator.core.exception.SagaGeneralException;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_SAVE_COMMAND_EXCEPTION;

public class SaveCommandException extends SagaGeneralException {
    public SaveCommandException(Throwable e) {
        super(SAGA_SAVE_COMMAND_EXCEPTION, e);
    }
}

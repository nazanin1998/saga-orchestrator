package com.nazaninfz.sagaorchestrator.exception.mongo;

import com.nazaninfz.sagaorchestrator.exception.SagaGeneralException;

import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_SAVE_COMMAND_EXCEPTION;

public class SaveCommandException extends SagaGeneralException {
    public SaveCommandException(Throwable e) {
        super(SAGA_SAVE_COMMAND_EXCEPTION, e);
    }
}

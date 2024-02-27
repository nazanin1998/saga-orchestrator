package com.nazaninfz.sagaorchestrator.exception.mongo;

import com.nazaninfz.sagaorchestrator.exception.SagaGeneralException;

import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_SAVE_COMMAND_EXCEPTION;
import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_SAVE_SEQUENCE_EXCEPTION;

public class SaveSequenceException extends SagaGeneralException {
    public SaveSequenceException(Throwable e) {
        super(SAGA_SAVE_SEQUENCE_EXCEPTION, e);
    }
}

package com.nazaninfz.sagaorchestrator.core.exception.mongo;

import com.nazaninfz.sagaorchestrator.core.exception.SagaGeneralException;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_SAVE_SEQUENCE_EXCEPTION;

public class SaveSequenceException extends SagaGeneralException {
    public SaveSequenceException(Throwable e) {
        super(SAGA_SAVE_SEQUENCE_EXCEPTION, e);
    }
}

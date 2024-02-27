package com.nazaninfz.sagaorchestrator.exception;

import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_UNKNOWN_COMMAND_TYPE_EXCEPTION;

public class UnknownCommandTypeException extends SagaGeneralException {
    public UnknownCommandTypeException() {
        super(SAGA_UNKNOWN_COMMAND_TYPE_EXCEPTION);
    }

    public UnknownCommandTypeException(Throwable e) {
        super(SAGA_UNKNOWN_COMMAND_TYPE_EXCEPTION, e);
    }
}

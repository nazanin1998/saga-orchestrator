package com.nazaninfz.sagaorchestrator.exception;

import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_INPUT_DECORATION_EXCEPTION;

public class InputDecorationException extends SagaGeneralException {
    public InputDecorationException(Throwable e) {
        super(SAGA_INPUT_DECORATION_EXCEPTION, e);
    }
}

package com.nazaninfz.sagaorchestrator.core.exception;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_UNSUPPORTED_OPERATION_EXCEPTION;

public class UnsupportedOperationException extends SagaGeneralException {
    public UnsupportedOperationException(Throwable e) {
        super(SAGA_UNSUPPORTED_OPERATION_EXCEPTION, e);
    }
}

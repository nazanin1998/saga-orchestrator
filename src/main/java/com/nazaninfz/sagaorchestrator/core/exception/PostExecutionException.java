package com.nazaninfz.sagaorchestrator.core.exception;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_POST_EXECUTION_EXCEPTION;

public class PostExecutionException extends SagaGeneralException {
    public PostExecutionException(Throwable e) {
        super(SAGA_POST_EXECUTION_EXCEPTION, e);
    }
}

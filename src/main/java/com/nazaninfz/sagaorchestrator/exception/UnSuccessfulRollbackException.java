
package com.nazaninfz.sagaorchestrator.exception;

import static com.nazaninfz.sagaorchestrator.exception.SagaExceptionEnum.SAGA_UNSUCCESSFUL_ROLLBACK_EXCEPTION;

public class UnSuccessfulRollbackException extends SagaGeneralException {
    public UnSuccessfulRollbackException(Throwable e) {
        super(SAGA_UNSUCCESSFUL_ROLLBACK_EXCEPTION, e);
    }
}

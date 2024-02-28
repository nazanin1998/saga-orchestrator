
package com.nazaninfz.sagaorchestrator.core.exception;

import static com.nazaninfz.sagaorchestrator.core.exception.SagaExceptionEnum.SAGA_UNSUCCESSFUL_ROLLBACK_EXCEPTION;

public class UnSuccessfulRollbackException extends SagaGeneralException {
    public UnSuccessfulRollbackException(Throwable e) {
        super(SAGA_UNSUCCESSFUL_ROLLBACK_EXCEPTION, e);
    }
}

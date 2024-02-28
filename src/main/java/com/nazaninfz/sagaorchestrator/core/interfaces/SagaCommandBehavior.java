package com.nazaninfz.sagaorchestrator.core.interfaces;

public interface SagaCommandBehavior {
    void commit();
    void rollback();
}

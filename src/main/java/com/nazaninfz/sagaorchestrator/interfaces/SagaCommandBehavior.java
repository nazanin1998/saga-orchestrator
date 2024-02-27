package com.nazaninfz.sagaorchestrator.interfaces;

public interface SagaCommandBehavior {
    void commit();
    void rollback();
}

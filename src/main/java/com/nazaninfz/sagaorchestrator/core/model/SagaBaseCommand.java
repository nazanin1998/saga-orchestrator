package com.nazaninfz.sagaorchestrator.core.model;

import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Accessors(chain = true)
public abstract class SagaBaseCommand implements Serializable {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}

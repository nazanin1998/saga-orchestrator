package com.nazaninfz.sagaorchestrator.core.model;

import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@NoArgsConstructor
@Accessors(chain = true)
public abstract class CommandInputDecorator extends SagaIdentifier {

    public abstract void decorate(
            SagaCommandInput input,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap);
}
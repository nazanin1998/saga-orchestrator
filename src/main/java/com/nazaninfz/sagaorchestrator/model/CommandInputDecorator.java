package com.nazaninfz.sagaorchestrator.model;

import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.SagaIdentifier;
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
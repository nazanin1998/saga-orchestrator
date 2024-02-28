package com.nazaninfz.sagaorchestrator.core.model;

import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(chain = true)
@NoArgsConstructor
public abstract class CommandExecutionCondition extends SagaIdentifier {

    public abstract boolean isSatisfied(
            SagaCommandInput input,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap);
}

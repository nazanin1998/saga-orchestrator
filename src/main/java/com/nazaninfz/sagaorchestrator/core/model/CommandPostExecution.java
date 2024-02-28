package com.nazaninfz.sagaorchestrator.core.model;

import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(chain = true)
@NoArgsConstructor
public abstract class CommandPostExecution extends SagaIdentifier {

    public abstract void execute(
            SagaCommandInput input,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap);
}

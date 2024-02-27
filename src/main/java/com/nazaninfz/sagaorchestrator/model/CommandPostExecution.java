package com.nazaninfz.sagaorchestrator.model;

import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.SagaIdentifier;
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

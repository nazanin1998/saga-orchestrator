package com.nazaninfz.sagaorchestrator.core.model;

import com.nazaninfz.sagaorchestrator.core.enums.RollbackRequired;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandBehavior;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.enums.OnExceptionBehavior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SagaCommand extends SagaBaseCommand implements SagaCommandBehavior {

    private String commandId;
    private String commandTitle;
    private String sequenceId;
    private int orderId;
    private SagaCommandInput input;
    private RollbackRequired rollbackRequired;
    private OnExceptionBehavior onExceptionBehavior;
    private CommandInputDecorator decorator;
    private List<CommandExecutionCondition> conditions;
    private List<CommandPostExecution> postExecutions;
    private List<SagaBaseCommand> nextCommands;
}

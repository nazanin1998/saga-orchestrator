package com.nazaninfz.sagaorchestrator.model;

import com.nazaninfz.sagaorchestrator.enums.OnExceptionBehavior;
import com.nazaninfz.sagaorchestrator.enums.RollbackRequired;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandBehavior;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandInput;
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

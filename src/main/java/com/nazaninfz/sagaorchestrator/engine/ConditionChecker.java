package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.core.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.core.enums.CommandStatus;
import com.nazaninfz.sagaorchestrator.core.enums.SagaCommandStepType;
import com.nazaninfz.sagaorchestrator.core.exception.ConditionCheckException;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.model.CommandExecutionCondition;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static com.nazaninfz.sagaorchestrator.core.enums.CommandStatus.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConditionChecker {
    private final SagaCommandServices commandServices;

    public boolean areAllConditionsSatisfied(
            List<CommandExecutionCondition> conditions,
            SagaCommandInput input,
            SagaCommandEntity commandEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        String commandId = commandEntity.getCommandId();
        String commandTitle = commandEntity.getCommandTitle();
        if (CollectionUtils.isEmpty(conditions)) {
            log.info("no conditions for command, id: {}, title: {}", commandId, commandTitle);
            saveNewStatus(commandEntity, CONDITIONS_CHECKING_NOT_NEEDED);
            return true;
        }
        log.info("start checking conditions of command, id: {}, title: {}", commandId, commandTitle);
        saveNewStatus(commandEntity, CONDITIONS_CHECKING_STARTED);

        for (CommandExecutionCondition condition : conditions) {
            boolean isSatisfied = isConditionSatisfied(condition, input, commandEntity, outputMap, contextMap);
            if (!isSatisfied) {
                commandEntity.addFailureStep(condition, SagaCommandStepType.CONDITION);
                saveNewStatus(commandEntity, CONDITIONS_CHECKING_FAILED);
                return false;
            }
            commandEntity.addSuccessStep(condition, SagaCommandStepType.CONDITION);
        }
        saveNewStatus(commandEntity, CONDITIONS_CHECKING_PASSED);
        return true;
    }

    private boolean isConditionSatisfied(
            CommandExecutionCondition condition,
            SagaCommandInput input,
            SagaCommandEntity commandEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        try {
            return condition.isSatisfied(input, outputMap, contextMap);
        } catch (Exception e) {
            log.error("exception in checking condition: {} command title: {}",
                    condition.getTitle(),
                    commandEntity.getCommandTitle()
            );
            commandEntity.addFailureStep(condition, SagaCommandStepType.CONDITION, e);
            saveNewStatus(commandEntity, CONDITIONS_CHECKING_FAILED);
            throw new ConditionCheckException(e);
        }
    }

    private void saveNewStatus(
            SagaCommandEntity commandEntity,
            CommandStatus newStatus
    ) {
        commandEntity = commandServices.saveCommand(commandEntity.setCommandStatus(newStatus));
    }

}

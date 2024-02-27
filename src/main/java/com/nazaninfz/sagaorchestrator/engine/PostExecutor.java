package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.enums.CommandStatus;
import com.nazaninfz.sagaorchestrator.enums.SagaCommandStepType;
import com.nazaninfz.sagaorchestrator.exception.PostExecutionException;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.CommandPostExecution;
import com.nazaninfz.sagaorchestrator.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static com.nazaninfz.sagaorchestrator.enums.CommandStatus.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostExecutor {

    private final SagaCommandServices commandServices;

    public void execute(
            List<CommandPostExecution> postExecutions,
            SagaCommandInput input,
            SagaCommandEntity commandEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        String commandId = commandEntity.getCommandId();
        String commandTitle = commandEntity.getCommandTitle();
        if (CollectionUtils.isEmpty(postExecutions)) {
            log.info("no post execution for command, id: {}, title: {}", commandId, commandTitle);
            saveNewStatus(commandEntity, POST_EXECUTION_NOT_NEEDED, commandServices);
            return;
        }
        log.info("start post executions for command, id: {}, title: {}",  commandId, commandTitle);
        saveNewStatus(commandEntity, POST_EXECUTION_STARTED, commandServices);

        for (CommandPostExecution postExecution : postExecutions) {
            doPostExecution(postExecution, input, commandEntity, outputMap, contextMap);
            commandEntity.addSuccessStep(postExecution, SagaCommandStepType.POST_EXECUTION);
        }
        saveNewStatus(commandEntity, POST_EXECUTION_PASSED, commandServices);
    }

    private void doPostExecution(
            CommandPostExecution postExecution,
            SagaCommandInput input,
            SagaCommandEntity commandEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        try {
            postExecution.execute(input, outputMap, contextMap);
        } catch (Exception e) {
            log.error("exception in post execution: {} command title: {}",
                    postExecution.getTitle(),
                    commandEntity.getCommandTitle()
            );
            commandEntity.addFailureStep(postExecution, SagaCommandStepType.POST_EXECUTION, e);
            saveNewStatus(commandEntity, POST_EXECUTION_FAILED, commandServices);
            throw new PostExecutionException(e);
        }
    }

    private void saveNewStatus(
            SagaCommandEntity commandEntity,
            CommandStatus newStatus,
            SagaCommandServices commandServices
    ) {
        commandEntity = commandServices.saveCommand(commandEntity.setCommandStatus(newStatus));
    }

}

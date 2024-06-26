package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.core.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.core.enums.CommandStatus;
import com.nazaninfz.sagaorchestrator.core.enums.SagaCommandStepType;
import com.nazaninfz.sagaorchestrator.core.exception.PostExecutionException;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.core.model.CommandPostExecution;
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
            saveNewStatus(commandEntity, POST_EXECUTION_NOT_NEEDED);
            return;
        }
        log.info("start post executions for command, id: {}, title: {}",  commandId, commandTitle);
        saveNewStatus(commandEntity, POST_EXECUTION_STARTED);

        for (CommandPostExecution postExecution : postExecutions) {
            doPostExecution(postExecution, input, commandEntity, outputMap, contextMap);
        }
        saveNewStatus(commandEntity, POST_EXECUTION_PASSED);
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
            commandEntity.addSuccessStep(postExecution, SagaCommandStepType.POST_EXECUTION);
        } catch (Exception e) {
            log.error("exception in post execution: {} command title: {}",
                    postExecution.getTitle(),
                    commandEntity.getCommandTitle()
            );
            commandEntity.addFailureStep(postExecution, SagaCommandStepType.POST_EXECUTION, e);
            saveNewStatus(commandEntity, POST_EXECUTION_FAILED);
            throw new PostExecutionException(e);
        }
    }

    private void saveNewStatus(
            SagaCommandEntity commandEntity,
            CommandStatus newStatus
    ) {
        commandEntity = commandServices.saveCommand(commandEntity.setCommandStatus(newStatus));
    }

}

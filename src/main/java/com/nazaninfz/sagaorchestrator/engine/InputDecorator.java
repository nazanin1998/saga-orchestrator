package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.enums.CommandStatus;
import com.nazaninfz.sagaorchestrator.enums.SagaCommandStepType;
import com.nazaninfz.sagaorchestrator.exception.InputDecorationException;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandInput;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.CommandInputDecorator;
import com.nazaninfz.sagaorchestrator.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.model.SagaCommand;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.nazaninfz.sagaorchestrator.enums.CommandStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InputDecorator {
    private final SagaCommandServices commandServices;

    public void decorate(
            CommandInputDecorator decorator,
            SagaCommandInput input,
            SagaCommandEntity commandEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        String commandId = commandEntity.getCommandId();
        String commandTitle = commandEntity.getCommandTitle();
        if (decorator == null) {
            log.info("no decorator for command, id: {}, title: {}", commandId, commandTitle);
            saveNewStatus(commandEntity, DECORATION_NOT_NEEDED, commandServices);
            return;
        }
        log.info("start input decoration for command, id: {}, title: {}", commandId, commandTitle);
        saveNewStatus(commandEntity, DECORATION_STARTED, commandServices);

        try {
            decorator.decorate(input, outputMap, contextMap);
            commandEntity.addSuccessStep(decorator, SagaCommandStepType.DECORATOR);
            saveNewStatus(commandEntity, DECORATION_PASSED, commandServices);
        } catch (Exception e) {
            log.error("exception in decorating: {} command title: {}",
                    decorator.getTitle(),
                    commandTitle
            );
            commandEntity.addFailureStep(decorator, SagaCommandStepType.DECORATOR, e);
            saveNewStatus(commandEntity, DECORATION_FAILED, commandServices);
            throw new InputDecorationException(e);
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

package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.core.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.core.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.core.enums.CommandStatus;
import com.nazaninfz.sagaorchestrator.core.enums.OnExceptionBehavior;
import com.nazaninfz.sagaorchestrator.core.exception.SagaGeneralException;
import com.nazaninfz.sagaorchestrator.core.exception.UnknownCommandTypeException;
import com.nazaninfz.sagaorchestrator.core.factory.SagaCommandFactory;
import com.nazaninfz.sagaorchestrator.core.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.core.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.core.model.SagaCommand;
import com.nazaninfz.sagaorchestrator.core.model.SagaSubSequence;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import com.nazaninfz.sagaorchestrator.service.SagaSequenceServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaCommandExecutor {
    private final SagaCommandFactory commandFactory;
    private final SagaCommandServices commandServices;
    private final SagaSequenceServices sequenceServices;
    private final InputDecorator inputDecorator;
    private final PostExecutor postExecutor;
    private final ConditionChecker conditionChecker;

    public void executeCommand(
            SagaBaseCommand command,
            Map<String, Object> contextMap,
            Map<String, SagaCommandOutput> outputMap,
            SagaSequenceEntity sequenceEntity
    ) {
        if (command instanceof SagaSubSequence) {
            executeCommand(
                    ((SagaSubSequence) command).getRootCommand(),
                    contextMap,
                    outputMap,
                    sequenceEntity);
            return;
        }
        if (!(command instanceof SagaCommand sagaCommand)) {
            log.error("UnknownCommandTypeException in execute command");
            throw new UnknownCommandTypeException();
        }
        sagaCommand.setSequenceId(sequenceEntity.getSequenceId());
        log.info("start executing command, sequenceId: {}, order: {}, id: {}, title: {}",
                sagaCommand.getSequenceId(),
                sagaCommand.getOrderId(),
                sagaCommand.getCommandId(),
                sagaCommand.getCommandTitle());
        SagaCommandEntity commandEntity = commandFactory.commandEntityFrom(sagaCommand)
                .save(commandServices);

        boolean areConditionsSatisfied = conditionChecker.areAllConditionsSatisfied(
                sagaCommand.getConditions(),
                sagaCommand.getInput(),
                commandEntity,
                outputMap,
                contextMap);

        if (!areConditionsSatisfied) return;

        inputDecorator.decorate(
                sagaCommand.getDecorator(),
                sagaCommand.getInput(),
                commandEntity,
                outputMap,
                contextMap
        );
        commitCommand(sagaCommand, commandEntity, sequenceEntity);
        postExecutor.execute(
                sagaCommand.getPostExecutions(),
                sagaCommand.getInput(),
                commandEntity,
                outputMap,
                contextMap
        );

        if (CollectionUtils.isEmpty(sagaCommand.getNextCommands())) {
            return;
        }

        for (SagaBaseCommand nextCommand : sagaCommand.getNextCommands()) {
            executeCommand(nextCommand, contextMap, outputMap, sequenceEntity);
        }
    }

    private void commitCommand(
            SagaCommand command,
            SagaCommandEntity commandEntity,
            SagaSequenceEntity sequenceEntity
    ) {
        try {
            commandEntity = commandEntity.saveNewStatus(CommandStatus.COMMITTING_STARTED, commandServices);
            command.commit();
            commandEntity = commandEntity.saveNewStatus(CommandStatus.COMMITTING_PASSED, commandServices);
            sequenceEntity = sequenceServices.saveSequence(sequenceEntity.addExecutedCommandId(command.getCommandId()));
        } catch (SagaGeneralException e) {
            handleCommittingException(command, commandEntity, e);
        } catch (Exception e) {
            handleCommittingException(command, commandEntity, new SagaGeneralException(e));
        }

    }

    private void handleCommittingException(SagaCommand command, SagaCommandEntity commandEntity, SagaGeneralException e) {
        log.error("exception in commit command id: {}, title: {}",
                command.getCommandId(),
                command.getCommandTitle());
        commandEntity.setExceptionSubText(ExceptionUtils.getStackTrace(e));

        if (command.getOnExceptionBehavior() == OnExceptionBehavior.ROLLBACK) {
            commandEntity.setCommandStatus(CommandStatus.COMMITTING_FAILED);
            commandEntity = commandEntity.save(commandServices);
            throw e;
        }
        if (command.getOnExceptionBehavior() == OnExceptionBehavior.SKIP) {
            commandEntity = commandEntity.saveNewStatus(CommandStatus.COMMITTING_SKIPPED, commandServices);
            return;
        }

        log.error("unsupported operation for on exception behaviour");
        throw new UnsupportedOperationException(e);
    }
}

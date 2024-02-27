package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.entity.SagaCommandEntity;
import com.nazaninfz.sagaorchestrator.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.exception.UnknownCommandTypeException;
import com.nazaninfz.sagaorchestrator.factory.SagaCommandFactory;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.model.SagaCommand;
import com.nazaninfz.sagaorchestrator.model.SagaSubSequence;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaCommandExecutor {
    private final SagaCommandFactory commandFactory;
    private final SagaCommandServices commandServices;
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

        if (!areConditionsSatisfied) {
            return;
        }
        inputDecorator.decorate(
                sagaCommand.getDecorator(),
                sagaCommand.getInput(),
                commandEntity,
                outputMap,
                contextMap
        );
//        command.commit();
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
}

package com.nazaninfz.sagaorchestrator.engine;

import com.nazaninfz.sagaorchestrator.entity.SagaSequenceEntity;
import com.nazaninfz.sagaorchestrator.enums.SequenceStatus;
import com.nazaninfz.sagaorchestrator.exception.SagaGeneralException;
import com.nazaninfz.sagaorchestrator.exception.UnSuccessfulRollbackException;
import com.nazaninfz.sagaorchestrator.factory.SagaSequenceFactory;
import com.nazaninfz.sagaorchestrator.interfaces.SagaCommandOutput;
import com.nazaninfz.sagaorchestrator.model.SagaBaseCommand;
import com.nazaninfz.sagaorchestrator.model.SagaCommand;
import com.nazaninfz.sagaorchestrator.model.SagaSequence;
import com.nazaninfz.sagaorchestrator.model.SagaSubSequence;
import com.nazaninfz.sagaorchestrator.service.SagaSequenceServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaSequenceExecutor {
    private final SagaSequenceServices sequenceServices;
    private final SagaSequenceFactory sequenceFactory;
    private final SagaCommandExecutor commandExecutor;

    public void startSequence(SagaSequence sequence) {

        SagaSequenceEntity sequenceEntity = sequenceServices.saveSequence(
                sequenceFactory.createSequence(sequence));

        log.info("start sequence {}", sequenceEntity);
        String id = sequenceEntity.getSequenceId();
        String title = sequenceEntity.getSequenceTitle();

        Map<String, SagaCommandOutput> outputMap = new HashMap<>();

        try {
            executeRootCommand(sequence.getRootCommand(), sequenceEntity, outputMap, sequence.getContextMap());
        } catch (UnSuccessfulRollbackException e) {
            log.error("UnSuccessfulRollbackException in sequence id: {}, title: {}", id, title, e);
            throw e;
        } catch (SagaGeneralException e) {
            log.error("SagaGeneralException in sequence, id: {}, title: {}", id, title, e);
            throw e;
        } catch (Exception e) {
            log.error("UnhandledException in sequence, id: {}, title: {}", id, title, e);
            sequenceEntity = sequenceEntity.saveNewStatus(SequenceStatus.UNKNOWN, sequenceServices);
            throw e;
        }

    }

    private void executeRootCommand(
            SagaBaseCommand rootCommand,
            SagaSequenceEntity sequenceEntity,
            Map<String, SagaCommandOutput> outputMap,
            Map<String, Object> contextMap
    ) {
        String id = sequenceEntity.getSequenceId();
        String title = sequenceEntity.getSequenceTitle();

        try {
            if (rootCommand instanceof SagaCommand) {
                commandExecutor.executeCommand(
                        rootCommand,
                        contextMap,
                        outputMap,
                        sequenceEntity);
            } else if (rootCommand instanceof SagaSubSequence) {
                commandExecutor.executeCommand(
                        ((SagaSubSequence) rootCommand).getRootCommand(),
                        contextMap,
                        outputMap,
                        sequenceEntity);
            }

            sequenceEntity = sequenceEntity.saveNewStatus(SequenceStatus.SUCCEED, sequenceServices);
            sendCompleteSignal(id);
            log.info("sequence completed successfully, id: {}, title: {}", id, title);
        } catch (Exception e) {
            log.error("exception in sequence, id: {}, title: {}", id, title, e);
            sequenceEntity = sequenceServices.saveSequence(
                    sequenceEntity.setExceptionSubText(ExceptionUtils.getStackTrace(e)));
            sendRollbackSignal(id);
            throw e;
        }
    }

    private void sendCompleteSignal(String sequenceId) {
        // todo send complete signal
    }

    private void sendRollbackSignal(String sequenceId) {
        // todo send rollback signal
    }
}

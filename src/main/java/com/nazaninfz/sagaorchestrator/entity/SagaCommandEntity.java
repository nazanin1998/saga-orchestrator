
package com.nazaninfz.sagaorchestrator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nazaninfz.sagaorchestrator.enums.*;
import com.nazaninfz.sagaorchestrator.factory.SagaCommandStepFactory;
import com.nazaninfz.sagaorchestrator.interfaces.*;
import com.nazaninfz.sagaorchestrator.model.*;
import com.nazaninfz.sagaorchestrator.service.SagaCommandServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document("saga_command")
public class SagaCommandEntity {

    @Id
    private String commandId;
    private String commandTitle;
    private String exceptionSubText;
    private int orderId;
    private String sequenceId;
    private SagaCommandInput input;
    private SagaCommandOutput output;
    private CommandStatus commandStatus;
    private RollbackRequired rollbackRequired;
    private OnExceptionBehavior onExceptionBehavior;
    private List<SagaCommandStepHistory> stepHistories;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonIgnore
    @Transient
    public void addStepHistory(SagaCommandStepHistory stepHistory) {
        if (CollectionUtils.isEmpty(stepHistories)) {
            stepHistories = new ArrayList<>();
        }
        stepHistories.add(stepHistory);
    }

    @JsonIgnore
    @Transient
    public SagaCommandEntity save(SagaCommandServices service) {
        return service.saveCommand(this);
    }

    @JsonIgnore
    @Transient
    public void addSuccessStep(
            SagaIdentifier identifier,
            SagaCommandStepType stepType
    ) {
        addStepHistory(SagaCommandStepFactory.success(identifier, stepType));
    }

    @JsonIgnore
    @Transient
    public void addFailureStep(
            SagaIdentifier identifier,
            SagaCommandStepType stepType
    ) {
        addStepHistory(SagaCommandStepFactory.failure(identifier, stepType));
    }

    @JsonIgnore
    @Transient
    public void addFailureStep(
            SagaIdentifier identifier,
            SagaCommandStepType stepType,
            Throwable e
    ) {
        addStepHistory(SagaCommandStepFactory.failure(identifier, stepType, e));
    }
}

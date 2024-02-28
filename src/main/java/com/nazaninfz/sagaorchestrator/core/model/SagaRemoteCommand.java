package com.nazaninfz.sagaorchestrator.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SagaRemoteCommand extends SagaCommand {

//    private Date sendRequestDate;
//    private Date receiveResponseDate;
    //    private Date sendRollbackDate;
//    private Date recveRollbackDate;
    private String exchange;
    private String routingKey;
    private Long timeout;

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }
}

package com.nazaninfz.sagaorchestrator.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SagaSequence extends SagaBaseCommand {

    private String sequenceId;
    private String sequenceTitle;
    private Map<String, Object> contextMap;
    private SagaBaseCommand rootCommand;

}

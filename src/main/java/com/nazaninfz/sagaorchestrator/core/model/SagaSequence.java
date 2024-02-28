package com.nazaninfz.sagaorchestrator.core.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;


@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SagaSequence {

    private String sequenceId;
    private String sequenceTitle;
    private Map<String, Object> contextMap;
    private SagaCommand rootCommand;
}

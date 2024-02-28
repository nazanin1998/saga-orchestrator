package com.nazaninfz.sagaorchestrator.core.model;

import lombok.*;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SagaSubSequence extends SagaBaseCommand {
    private SagaCommand rootCommand;
}

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
public class SagaSubSequence extends SagaBaseCommand {
    private SagaBaseCommand rootCommand;
}

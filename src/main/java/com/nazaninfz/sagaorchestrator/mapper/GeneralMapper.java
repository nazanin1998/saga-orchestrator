package com.nazaninfz.sagaorchestrator.mapper;

public interface GeneralMapper<M, D> {
    D modelToDto(M m);

    M dtoToModel(D d);
}

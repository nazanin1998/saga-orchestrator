package com.nazaninfz.sagaorchestrator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SagaGeneralException extends RuntimeException {
    public static final String SAGA_SERVICE = "SAGA_SERVICE";
    public static final String SAGA_SERVICE_NUM = "1111";

    private final String serviceName;
    private final String serviceNumber;
    private final String details = "";
    private final String localeMessage = "";
    private Integer exceptionNumber = 0;
    private final HttpStatus statusCode;

    public SagaGeneralException(SagaExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.serviceName = SAGA_SERVICE;
        this.serviceNumber = SAGA_SERVICE_NUM;
        this.statusCode = exceptionEnum.getStatusCode();
        this.exceptionNumber = exceptionEnum.getExceptionNumber();
    }

    public SagaGeneralException(SagaExceptionEnum exceptionEnum, Throwable e) {
        super(exceptionEnum.getMessage(), e);
        this.serviceName = SAGA_SERVICE;
        this.serviceNumber = SAGA_SERVICE_NUM;
        this.statusCode = exceptionEnum.getStatusCode();
        this.exceptionNumber = exceptionEnum.getExceptionNumber();
    }

    public SagaGeneralException(HttpStatus statusCode, String message) {
        super(message);
        this.serviceName = SAGA_SERVICE;
        this.serviceNumber = SAGA_SERVICE_NUM;
        this.statusCode = statusCode;
        this.exceptionNumber = 0;
    }
}

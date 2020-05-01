package com.thechetankrishna.mysqlfailover.exceptions;

import lombok.Data;

@Data
public class GenericException {

    private String exceptionMessage;
    private String exceptionResponseCode;
    private String exceptionDateTime;

    public GenericException(String exceptionMessage, String exceptionResponseCode, String exceptionDateTime) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionResponseCode = exceptionResponseCode;
        this.exceptionDateTime = exceptionDateTime;
    }
}

package com.thechetankrishna.mysqlfailover.exceptions;

public class NoCustomerExistsException extends RuntimeException {

    public NoCustomerExistsException(String message) {
        super(message);
    }
}

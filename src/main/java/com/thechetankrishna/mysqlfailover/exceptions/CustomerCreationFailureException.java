package com.thechetankrishna.mysqlfailover.exceptions;

public class CustomerCreationFailureException extends RuntimeException{

    public CustomerCreationFailureException(String message) {
        super(message);
    }
}

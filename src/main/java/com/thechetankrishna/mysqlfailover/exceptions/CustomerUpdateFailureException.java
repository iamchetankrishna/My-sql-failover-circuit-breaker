package com.thechetankrishna.mysqlfailover.exceptions;

public class CustomerUpdateFailureException extends RuntimeException{

    public CustomerUpdateFailureException(String message) {
        super(message);
    }
}

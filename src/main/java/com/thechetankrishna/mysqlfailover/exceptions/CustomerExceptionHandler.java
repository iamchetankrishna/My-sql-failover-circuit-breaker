package com.thechetankrishna.mysqlfailover.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<GenericException> customerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        GenericException genericException = new GenericException(customerNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoCustomerExistsException.class)
    public ResponseEntity<GenericException> noCustomerExistsExeption(NoCustomerExistsException noCustomerExistsException) {
        GenericException genericException = new GenericException(noCustomerExistsException.getMessage(),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CustomerCreationFailureException.class)
    public ResponseEntity<GenericException> customerCreationFailureException(CustomerCreationFailureException customerCreationFailureException) {
        GenericException genericException = new GenericException(customerCreationFailureException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CustomerUpdateFailureException.class)
    public ResponseEntity<GenericException> customerUpdateFailureException(CustomerUpdateFailureException customerUpdateFailureException) {
        GenericException genericException = new GenericException(customerUpdateFailureException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<GenericException> numberFormatException(NumberFormatException numberFormatException) {
        GenericException genericException = new GenericException(numberFormatException.toString(),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<GenericException> illegalArgumentException(IllegalArgumentException illegalArgumentException) {
        GenericException genericException = new GenericException(illegalArgumentException.toString(),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().toString());
        return new ResponseEntity<>(genericException, null, HttpStatus.BAD_REQUEST);
    }
}

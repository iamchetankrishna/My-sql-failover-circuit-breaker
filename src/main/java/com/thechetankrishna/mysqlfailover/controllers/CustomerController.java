package com.thechetankrishna.mysqlfailover.controllers;

import com.thechetankrishna.mysqlfailover.circuitBreakerDB.CustomerServiceCircuitBreaker;
import com.thechetankrishna.mysqlfailover.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private CustomerServiceCircuitBreaker customerServiceCircuitBreaker;

    @Autowired
    public CustomerController(CustomerServiceCircuitBreaker customerServiceCircuitBreaker) {
        this.customerServiceCircuitBreaker = customerServiceCircuitBreaker;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCustomer() {
        List<Customer> customerList = this.customerServiceCircuitBreaker.getAllCustomer();
        return new ResponseEntity<List<Customer>>(customerList, null, HttpStatus.OK);
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
        Customer customer = this.customerServiceCircuitBreaker.getCustomerById(customerId);
        return new ResponseEntity<Customer>(customer, null, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customerRequest) {
        Customer customer = this.customerServiceCircuitBreaker.addCustomer(customerRequest);
        return new ResponseEntity<Customer>(customer, null, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Integer customerId,
                                            @RequestBody Customer customerRequest) {
        Customer customer = this.customerServiceCircuitBreaker.updateCustomer(customerRequest);
        return new ResponseEntity<Customer>(customer, null, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
        this.customerServiceCircuitBreaker.deleteCustomer(customerId);
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }
}

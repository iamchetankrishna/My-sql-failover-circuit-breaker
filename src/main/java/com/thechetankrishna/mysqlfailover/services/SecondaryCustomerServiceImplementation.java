package com.thechetankrishna.mysqlfailover.services;

import com.thechetankrishna.mysqlfailover.exceptions.CustomerCreationFailureException;
import com.thechetankrishna.mysqlfailover.exceptions.CustomerNotFoundException;
import com.thechetankrishna.mysqlfailover.exceptions.CustomerUpdateFailureException;
import com.thechetankrishna.mysqlfailover.exceptions.NoCustomerExistsException;
import com.thechetankrishna.mysqlfailover.interfaces.CustomerService;
import com.thechetankrishna.mysqlfailover.model.Customer;
import com.thechetankrishna.mysqlfailover.secondaryRepository.SecondaryCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecondaryCustomerServiceImplementation implements CustomerService {

    private SecondaryCustomerRepository secondaryCustomerRepository;

    @Autowired
    public SecondaryCustomerServiceImplementation(SecondaryCustomerRepository secondaryCustomerRepository) {
        this.secondaryCustomerRepository = secondaryCustomerRepository;
    }

    @Override
    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = checkCustomerExists(this.secondaryCustomerRepository, customerId);
        if(customerOptional.isPresent()) {
            return customerOptional.get();
        }else {
            throw new CustomerNotFoundException(("Customer with Customer ID : " + customerId + " not found"));
        }
    }

    @Override
    public List<Customer> getAllCustomer() throws NoCustomerExistsException {
        Iterable<Customer> customerIterable = this.secondaryCustomerRepository.findAll();
        List<Customer> customerList;
        if(this.secondaryCustomerRepository.count() > 0) {
            customerList = new ArrayList<>();
            customerIterable.forEach(customer -> customerList.add(customer));
            return customerList;
        }else {
            throw new CustomerNotFoundException("Database is empty. No Customer Exists !");
        }
    }

    @Override
    public Customer addCustomer(Customer customer) throws CustomerCreationFailureException {
        if(validateCustomer(customer)) {
            this.secondaryCustomerRepository.save(customer);
            return customer;
        }else {
            throw new CustomerCreationFailureException("Unable to create customer because of invalid input !!");
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerUpdateFailureException, CustomerNotFoundException {
        if(checkCustomerExists(this.secondaryCustomerRepository, customer.getCustomerId()).isPresent()) {
            if(validateCustomer(customer)) {
                this.secondaryCustomerRepository.save(customer);
                return customer;
            }else {
                throw new CustomerUpdateFailureException("Unable to create customer because of invalid input !!");
            }
        }else {
            throw new CustomerNotFoundException("No Such Customer exists with Id : " + customer.getCustomerId());
        }
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        if(checkCustomerExists(this.secondaryCustomerRepository, customerId).isPresent()) {
            this.secondaryCustomerRepository.deleteById(customerId);
        }else {
            throw new CustomerNotFoundException("No Such Customer exists with Id : " + customerId);
        }
    }
}

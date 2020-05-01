package com.thechetankrishna.mysqlfailover.interfaces;

import com.thechetankrishna.mysqlfailover.exceptions.CustomerCreationFailureException;
import com.thechetankrishna.mysqlfailover.exceptions.CustomerNotFoundException;
import com.thechetankrishna.mysqlfailover.exceptions.CustomerUpdateFailureException;
import com.thechetankrishna.mysqlfailover.exceptions.NoCustomerExistsException;
import com.thechetankrishna.mysqlfailover.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerService {


    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException;
    public List<Customer> getAllCustomer() throws NoCustomerExistsException;
    public Customer addCustomer(Customer customer) throws CustomerCreationFailureException;
    public Customer updateCustomer(Customer customer) throws CustomerUpdateFailureException, CustomerNotFoundException;
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    public default boolean validateCustomer(Customer customer) {
        if(customer.getCustomerEmail() != null && !customer.getCustomerEmail().equals("")) {
            if (customer.getCustomerName() != null && !customer.getCustomerName().equals("")) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    public default Optional<Customer> checkCustomerExists(JpaRepository<Customer, Long> customerRepository, Long customerId) {
        return customerRepository.findById(customerId);
    }
}

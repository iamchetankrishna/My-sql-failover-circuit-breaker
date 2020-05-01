package com.thechetankrishna.mysqlfailover.circuitBreakerDB;

import com.thechetankrishna.mysqlfailover.model.Customer;
import com.thechetankrishna.mysqlfailover.services.PrimaryCustomerServiceImplementation;
import com.thechetankrishna.mysqlfailover.services.SecondaryCustomerServiceImplementation;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceCircuitBreaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceCircuitBreaker.class);

    private PrimaryCustomerServiceImplementation primaryCustomerServiceImplementation;
    private SecondaryCustomerServiceImplementation secondaryCustomerServiceImplementation;

    @Autowired
    public CustomerServiceCircuitBreaker(PrimaryCustomerServiceImplementation primaryCustomerServiceImplementation,
                                         SecondaryCustomerServiceImplementation secondaryCustomerServiceImplementation) {
        this.primaryCustomerServiceImplementation = primaryCustomerServiceImplementation;
        this.secondaryCustomerServiceImplementation = secondaryCustomerServiceImplementation;
    }

    @CircuitBreaker(name = "databaseOperation", fallbackMethod = "fallBackGetAllCustomer")
    public List<Customer> getAllCustomer() {
        LOGGER.info("In GetAllCustomer Method using Primary DB");
        return this.primaryCustomerServiceImplementation.getAllCustomer();
    }

    public List<Customer> fallBackGetAllCustomer(Throwable throwable) {
        LOGGER.info("In fallBackGetAllCustomer Method Primary DB Unavailable");
        LOGGER.info(throwable.getClass().getName());
        if (throwable instanceof CannotCreateTransactionException || throwable instanceof CallNotPermittedException) {
            LOGGER.info("In fallBackGetAllCustomer Using Secondary DB NOW");
            return this.secondaryCustomerServiceImplementation.getAllCustomer();
        } else {
            LOGGER.info("In fallBackGetAllCustomer Both Primary DB and Secondary DB is down");
            LOGGER.info("In fallBackGetAllCustomer Returning Default Values");
            Customer defaultCustomer = new Customer();
            defaultCustomer.setCustomerId(-1);
            defaultCustomer.setCustomerName("Default USER");
            defaultCustomer.setCustomerEmail("Default EMAIL");
            List<Customer> defaultCustomerList = new ArrayList<>();
            defaultCustomerList.add(defaultCustomer);
            return defaultCustomerList;
        }
    }

    @CircuitBreaker(name = "databaseOperation", fallbackMethod = "fallBackGetCustomerById")
    public Customer getCustomerById(Long customerId) {
        LOGGER.info("In GetCustomerByID using Primary DB");
        return this.primaryCustomerServiceImplementation.getCustomerById(customerId);
    }

    public Customer fallBackGetCustomerById(Long customerId, Throwable throwable) {
        LOGGER.info("In fallBackGetCustomerById Method Primary DB Unavailable");
        LOGGER.info(throwable.getClass().getName());
        if (throwable instanceof CannotCreateTransactionException || throwable instanceof CallNotPermittedException) {
            LOGGER.info("In fallBackGetCustomerById Method Using Secondary DB NOW");
            return this.secondaryCustomerServiceImplementation.getCustomerById(customerId);
        } else {
            LOGGER.info("In fallBackGetCustomerById Method Both Primary DB and Secondary DB is down");
            LOGGER.info("In fallBackGetCustomerById Method Returning Default Values");
            Customer defaultCustomer = new Customer();
            defaultCustomer.setCustomerId(-1);
            defaultCustomer.setCustomerName("Default USER");
            defaultCustomer.setCustomerEmail("Default EMAIL");
            return defaultCustomer;
        }
    }

    @CircuitBreaker(name = "databaseOperation", fallbackMethod = "fallBackAddCustomer")
    public Customer addCustomer(Customer customer) {
        LOGGER.info("In AddCustomer using Primary DB");
        return this.primaryCustomerServiceImplementation.addCustomer(customer);
    }

    public Customer fallBackAddCustomer(Customer customer, Throwable throwable) {
        LOGGER.info("In fallBackAddCustomer Method Primary DB Unavailable");
        LOGGER.info(throwable.getClass().getName());
        if (throwable instanceof CannotCreateTransactionException || throwable instanceof CallNotPermittedException) {
            LOGGER.info("In fallBackAddCustomer Method Using Secondary DB NOW");
            return this.secondaryCustomerServiceImplementation.addCustomer(customer);
        } else {
            LOGGER.info("In fallBackAddCustomer Method Both Primary DB and Secondary DB is down");
            LOGGER.info("In fallBackAddCustomer Method Returning Default Values");
            Customer defaultCustomer = new Customer();
            defaultCustomer.setCustomerId(-1);
            defaultCustomer.setCustomerName("Default USER");
            defaultCustomer.setCustomerEmail("Default EMAIL");
            return defaultCustomer;
        }
    }

    @CircuitBreaker(name = "databaseOperation", fallbackMethod = "fallBackUpdateCustomer")
    public Customer updateCustomer(Customer customer) {
        LOGGER.info("In UpdateCustomer using Primary DB");
        return this.primaryCustomerServiceImplementation.updateCustomer(customer);
    }

    public Customer fallBackUpdateCustomer(Customer customer, Throwable throwable) {
        LOGGER.info("In fallBackUpdateCustomer Method Primary DB Unavailable");
        LOGGER.info(throwable.getClass().getName());
        if (throwable instanceof CannotCreateTransactionException || throwable instanceof CallNotPermittedException) {
            LOGGER.info("In fallBackUpdateCustomer Method Using Secondary DB NOW");
            return this.secondaryCustomerServiceImplementation.updateCustomer(customer);
        } else {
            LOGGER.info("In fallBackUpdateCustomer Method Both Primary DB and Secondary DB is down");
            LOGGER.info("In fallBackUpdateCustomer Method Returning Default Values");
            Customer defaultCustomer = new Customer();
            defaultCustomer.setCustomerId(-1);
            defaultCustomer.setCustomerName("Default USER");
            defaultCustomer.setCustomerEmail("Default EMAIL");
            return defaultCustomer;
        }
    }

    @CircuitBreaker(name = "databaseOperation", fallbackMethod = "fallBackDeleteCustomer")
    public void deleteCustomer(Long customerId) {
        LOGGER.info("In DeleteCustomer using Primary DB");
        this.primaryCustomerServiceImplementation.deleteCustomer(customerId);
    }

    public void fallBackDeleteCustomer(Long customerId, Throwable throwable) {
        LOGGER.info("In fallBackDeleteCustomer Method Primary DB Unavailable");
        LOGGER.info(throwable.getClass().getName());
        if (throwable instanceof CannotCreateTransactionException || throwable instanceof CallNotPermittedException) {
            LOGGER.info("In fallBackDeleteCustomer Method Using Secondary DB NOW");
            this.secondaryCustomerServiceImplementation.deleteCustomer(customerId);
        } else {
            LOGGER.info("In fallBackDeleteCustomer Method Both Primary DB and Secondary DB is down");
            LOGGER.info("In fallBackDeleteCustomer Method Delete Operation Failed");
            return;
        }
    }
}

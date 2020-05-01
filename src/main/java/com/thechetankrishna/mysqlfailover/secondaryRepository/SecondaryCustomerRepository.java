package com.thechetankrishna.mysqlfailover.secondaryRepository;

import com.thechetankrishna.mysqlfailover.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryCustomerRepository extends JpaRepository<Customer, Long> {

}

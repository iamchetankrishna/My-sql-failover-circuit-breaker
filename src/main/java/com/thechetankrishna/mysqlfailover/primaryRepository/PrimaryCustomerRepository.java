package com.thechetankrishna.mysqlfailover.primaryRepository;

import com.thechetankrishna.mysqlfailover.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryCustomerRepository extends JpaRepository<Customer, Long> {

}

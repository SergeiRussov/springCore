package org.russow.repository;

import org.russow.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerRepository<Entity extends Customer> {

    List<Entity> getCustomers();
}

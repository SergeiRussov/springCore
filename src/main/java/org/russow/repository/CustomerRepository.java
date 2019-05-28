package org.russow.repository;

import org.russow.model.Customer;

import java.util.List;

public interface CustomerRepository<Entity extends Customer> {

    List<Entity> getCustomers();
}

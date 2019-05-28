package org.russow.repository.impl;

import org.russow.repository.CustomerRepository;
import org.russow.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CustomerRepositoryImpl implements CustomerRepository<Customer> {

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Customer> getCustomers() {

        final List<Customer> customers = entityManager.createQuery("from Customer", Customer.class).getResultList();

        entityManager.close();

        return customers;
    }
}

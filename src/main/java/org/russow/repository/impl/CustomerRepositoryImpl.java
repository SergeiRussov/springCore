package org.russow.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Customer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CustomerRepositoryImpl implements CustomerRepository<Customer> {

    @PersistenceContext()
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Override
    public List<Customer> getCustomers() {
        transaction = entityManager.getTransaction();
        transaction.begin();

        final List<Customer> customers = entityManager.createQuery("from Customer", Customer.class).getResultList();

        transaction.commit();
        entityManager.close();

        return customers;
    }
}

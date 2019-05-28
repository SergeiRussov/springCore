package org.russow.repository.impl;

import org.russow.model.Order;
import org.russow.repository.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class OrderRepositoryImpl implements OrderRepository<Order> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean addOrder(Order order) {
        boolean result;

        entityManager.persist(order);
        entityManager.close();

        result = true;

        return result;
    }
}

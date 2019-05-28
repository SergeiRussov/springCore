package org.russow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.russow.model.Order;
import org.russow.service.OrderService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService<Order> {

    @PersistenceContext
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Override
    public boolean addOrder(Order order) {
        boolean result;

        transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(order);
        transaction.commit();

        entityManager.close();

        result = true;

        return result;
    }

    enum SQLOrderService {

        ADD_CUSTOMER_ORDERS_DEP("INSERT INTO customers_orders VALUES (?, ?)"),
        ADD_ORDER_GOOD_DEP("INSERT INTO goods_orders VALUES (?,?)");

        String QUERY;

        SQLOrderService(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

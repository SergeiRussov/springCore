package org.russow.service;

import org.russow.model.Order;
import org.springframework.stereotype.Component;

@Component
public interface OrderService<Entity extends Order> {

    boolean addOrder(Entity order);
}

package org.russow.service;

import org.russow.model.Order;

public interface OrderService<Entity extends Order> {

    boolean addOrder(Entity order);
}

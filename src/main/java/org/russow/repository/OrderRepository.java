package org.russow.repository;

import org.russow.model.Order;

public interface OrderRepository<Entity extends Order> {

    boolean addOrder(Entity entity);
}

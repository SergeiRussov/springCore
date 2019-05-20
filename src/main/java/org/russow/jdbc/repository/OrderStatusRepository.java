package org.russow.jdbc.repository;

import org.russow.model.OrderStatus;

import java.util.List;

public interface OrderStatusRepository<Entity extends OrderStatus> {

    List<Entity> getOrderStatusList();
}

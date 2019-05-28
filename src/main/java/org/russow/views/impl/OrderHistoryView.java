package org.russow.views.impl;

import org.russow.model.Order;
import org.russow.views.Executable;
import org.russow.views.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderHistoryView implements Executable {

    @Override
    public void run() {
        List<Order> orders = (List<Order>) Menu.getCustomer().getOrders();

        for (Order order : orders) {
            System.out.println("ID заказа: " + order.getId() + ", дата заказа: " + order.getDate() +
                    ", общая сумма заказа: " + order.getTotalPrice() + ", статус заказа: " + order.getStatus().getName());
        }
    }
}

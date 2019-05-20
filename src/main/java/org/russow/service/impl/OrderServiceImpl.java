package org.russow.service.impl;

import lombok.AllArgsConstructor;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.impl.OrderRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.model.Order;
import org.russow.service.OrderService;
import org.russow.views.Menu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService<Order> {

    private JDBCUtils driver;
    private OrderRepositoryImpl orderRepository;

    @Override
    public boolean addOrder(Order order) {
        boolean result = false;

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLOrder.ADD_ORDER.QUERY)) {
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setInt(2, order.getTotalPrice());
            statement.setInt(3, 1);
            statement.setInt(4, order.getCoupon().getId());

            statement.executeUpdate();

            List<Order> lastOrders = orderRepository.getOrders();
            Order lastOrder = lastOrders.get(lastOrders.size() - 1);

            for (Good good : order.getGoods()) {
                addGoodsOrderDep(lastOrder.getId(), good.getId(), connection);
            }

            addCustOrderDep(Menu.getCustomer().getId(), lastOrder.getId(), connection);

            result = true;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    private boolean addCustOrderDep(int customerId, int orderId, Connection connection) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLOrder.ADD_CUSTOMER_ORDERS_DEP.QUERY)) {
            statement.setInt(1, customerId);
            statement.setInt(2, orderId);

            statement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    private boolean addGoodsOrderDep(int orderId, int goodId, Connection connection) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLOrder.ADD_ORDER_GOOD_DEP.QUERY)) {
            statement.setInt(1, orderId);
            statement.setInt(2, goodId);

            statement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    enum SQLOrder {

        ADD_ORDER("INSERT INTO orders (order_date, total_price, status_id, coupon_id) " +
                "VALUES (?, ?, ?, ?)"),
        ADD_CUSTOMER_ORDERS_DEP("INSERT INTO customers_orders VALUES (?, ?)"),
        ADD_ORDER_GOOD_DEP("INSERT INTO goods_orders VALUES (?,?)");

        String QUERY;

        SQLOrder(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

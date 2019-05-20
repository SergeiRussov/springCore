package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.model.Order;
import org.russow.views.Menu;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository<Order> {

    private JDBCUtils driver;
    private OrderStatusRepositoryImpl orderStatusRepository;
    private CouponRepositoryImpl couponRepository;
    private GoodRepositoryImpl goodRepository;

    @Override
    public List<Order> getOrdersFromCustomerId(int customerId) {
        final List<Order> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLOrder.GET_ORDERS_FROM_CUSTOMER_ID.QUERY)) {
            statement.setInt(1, customerId);
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Order order = new Order();

                int orderId = rs.getInt("id");
                order.setId(orderId);
                order.setDate(LocalDate.parse(rs.getString("order_date")));
                order.setTotalPrice(rs.getInt("total_price"));

                int statusId = rs.getInt("status_id");
                order.setStatus(orderStatusRepository.getOrderStatusFromOrderId(statusId));

                int couponId = rs.getInt("coupon_id");
                order.setCoupon(couponRepository.getCouponFromId(couponId));

                order.setGoods(goodRepository.getGoodsFromOrderId(orderId));

                result.add(order);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    public List<Order> getOrders() {
        final List<Order> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLOrder.GET_ORDERS.QUERY)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Order order = new Order();

                int orderId = rs.getInt("id");
                order.setId(orderId);
                order.setDate(LocalDate.parse(rs.getString("order_date")));
                order.setTotalPrice(rs.getInt("total_price"));

                int statusId = rs.getInt("status_id");
                order.setStatus(orderStatusRepository.getOrderStatusFromOrderId(statusId));

                int couponId = rs.getInt("coupon_id");
                order.setCoupon(couponRepository.getCouponFromId(couponId));

                order.setGoods(goodRepository.getGoodsFromOrderId(orderId));

                result.add(order);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLOrder {

        GET_ORDERS("SELECT * FROM orders"),
        GET_ORDERS_FROM_CUSTOMER_ID("SELECT * FROM orders WHERE id IN (SELECT order_id FROM customers_orders " +
                "WHERE customer_id = ?)");

        String QUERY;

        SQLOrder(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

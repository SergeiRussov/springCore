package org.russow.jdbc.repository.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.OrderStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class OrderStatusRepositoryImpl implements OrderStatusRepository<OrderStatus> {

    private JDBCUtils driver;
    private OrderStatus orderStatus;

    @Override
    public List<OrderStatus> getOrderStatusList() {
        final List<OrderStatus> orderStatuses = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLOrderStatus.GET_ORDER_STATUSES.QUERY)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final OrderStatus orderStatus = new OrderStatus();

                orderStatus.setId(rs.getInt("id"));
                orderStatus.setName(rs.getString("name"));

                orderStatuses.add(orderStatus);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return orderStatuses;
    }

    public OrderStatus getOrderStatusFromOrderId(int id) {
        for (OrderStatus temp : getOrderStatusList()) {
            if (temp.getId() == id) {
                orderStatus = temp;
            }
        }

        return orderStatus;
    }

    enum SQLOrderStatus {

        GET_ORDER_STATUSES("SELECT * FROM order_status");

        String QUERY;

        SQLOrderStatus(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

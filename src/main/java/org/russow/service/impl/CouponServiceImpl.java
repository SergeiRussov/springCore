package org.russow.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.russow.jdbc.JDBCUtils;
import org.russow.service.CouponService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {

    private JDBCUtils driver;

    @Override
    public boolean addCoupon(int discount) {
        boolean result = false;

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLCoupon.ADD_COUPON.QUERY)) {
            statement.setInt(1, discount);

            statement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLCoupon {

        ADD_COUPON("INSERT INTO coupons (discount) VALUES (?)");

        String QUERY;

        SQLCoupon(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

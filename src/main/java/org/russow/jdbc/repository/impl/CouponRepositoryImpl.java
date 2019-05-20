package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class CouponRepositoryImpl implements CouponRepository<Coupon> {

    private JDBCUtils driver;
    private Coupon coupon;

    @Override
    public List<Coupon> getCoupons() {
        final List<Coupon> coupons = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLCoupon.GET_COUPONS.QUERY)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Coupon coupon = new Coupon();

                coupon.setId(rs.getInt("id"));
                coupon.setDiscount(rs.getInt("discount"));

                coupons.add(coupon);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return coupons;
    }

    public Coupon getCouponFromId(int id) {
        coupon.setId(0);
        coupon.setDiscount(0);

        for (Coupon temp : getCoupons()) {
            if (temp.getId() == id) {
                coupon = temp;
            }
        }

        return coupon;
    }

    enum SQLCoupon {

        GET_COUPONS("SELECT * FROM coupons");

        String QUERY;

        SQLCoupon(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

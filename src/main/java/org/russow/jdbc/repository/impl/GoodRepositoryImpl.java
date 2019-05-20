package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.GoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class GoodRepositoryImpl implements GoodRepository<Good> {

    private JDBCUtils driver;

    @Override
    public List<Good> getGoodsFromCategory(int categoryId) {
        final List<Good> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLGood.GET_GOODS_FROM_CAT.QUERY)) {
            statement.setInt(1, categoryId);
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Good good = new Good();

                good.setId(rs.getInt("id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getInt("price"));

                result.add(good);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    public List<Good> getGoodsFromOrderId(int orderId) {
        final List<Good> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLGood.GET_GOODS_FROM_ORDER_ID.QUERY)) {
            statement.setInt(1, orderId);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final Good good = new Good();

                good.setId(Integer.parseInt(rs.getString("id")));
                good.setName(rs.getString("name"));
                good.setPrice(Integer.parseInt(rs.getString("price")));

                result.add(good);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLGood {

        GET_GOODS_FROM_CAT("SELECT * FROM goods WHERE category_id = ?"),
        GET_GOODS_FROM_ORDER_ID("SELECT * FROM goods WHERE id IN (SELECT good_id FROM goods_orders WHERE order_id = ?)");

        String QUERY;

        SQLGood(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

package org.russow.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.russow.jdbc.JDBCUtils;
import org.russow.model.Good;
import org.russow.service.GoodService;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class GoodServiceImpl implements GoodService {

    private JDBCUtils driver;
    private Gson gson;

    @Override
    public boolean addGoods(File file) {
        Type itemsListType = new TypeToken<List<Good>>() {}.getType();
        boolean result = false;

        try (Reader reader = new FileReader(file)) {
            List<Good> newGoods = gson.fromJson(reader, itemsListType);

            for (Good good : newGoods) {
                result = addGoodFromBase(good);
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    private boolean addGoodFromBase(Good newGood) {
        boolean result = false;

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLGood.ADD_GOOD.QUERY)) {
            statement.setString(1, newGood.getName());
            statement.setInt(2, newGood.getPrice());
            statement.setInt(3, newGood.getCategoryId());

            statement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLGood {

        ADD_GOOD("INSERT INTO goods (name, price, category_id) VALUES (?, ?, ?)");

        String QUERY;

        SQLGood(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

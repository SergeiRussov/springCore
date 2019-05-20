package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository<Category> {

    private JDBCUtils driver;
    private GoodRepositoryImpl goodRepository;

    @Override
    public List<Category> getCategories() {
        final List<Category> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLCategory.GET_CATEGORIES.QUERY)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Category category = new Category();

                int categoryId = rs.getInt("id");
                category.setId(categoryId);
                category.setName(rs.getString("name"));
                category.setGoods(goodRepository.getGoodsFromCategory(categoryId));

                result.add(category);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLCategory {

        GET_CATEGORIES("SELECT * FROM categories");

        String QUERY;

        SQLCategory(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

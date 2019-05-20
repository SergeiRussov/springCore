package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository<Role> {

    private JDBCUtils driver;
    private Role role;

    @Override
    public List<Role> getRoles() {
        final List<Role> roles = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLRole.GET_ROLES.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                role = new Role();

                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));

                roles.add(role);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return roles;
    }

    public Role getRoleFromCustomerId(int id) {
        for (Role temp : getRoles()) {
            if (temp.getId() == id) {
                role = temp;
            }
        }

        return role;
    }

    enum SQLRole {

        GET_ROLES("SELECT * FROM roles");

        String QUERY;

        SQLRole(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

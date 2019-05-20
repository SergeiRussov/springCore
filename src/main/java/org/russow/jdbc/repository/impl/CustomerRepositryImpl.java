package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.ContactRepository;
import org.russow.jdbc.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.jdbc.repository.OrderRepository;
import org.russow.jdbc.repository.RoleRepository;
import org.russow.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class CustomerRepositryImpl implements CustomerRepository<Customer> {

    private JDBCUtils driver;
    private Customer customer;
    private ContactRepositoryImpl contactRepository;
    private RoleRepositoryImpl roleRepository;
    private OrderRepositoryImpl orderRepository;

    @Override
    public List<Customer> getCustomers() {
        final List<Customer> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLCustomer.GET_CUSTOMERS.QUERY)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                customer = new Customer();

                int customerId = rs.getInt("id");
                customer.setId(customerId);

                customer.setName(rs.getString("name"));

                int contactId = rs.getInt("contact_id");
                customer.setContacts(contactRepository.getContactsFromId(contactId));

                customer.setRole(roleRepository.getRoleFromCustomerId(customerId));

                customer.setOrders(orderRepository.getOrdersFromCustomerId(customerId));

                result.add(customer);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLCustomer {

        GET_CUSTOMERS("SELECT * FROM CUSTOMERS");

        String QUERY;

        SQLCustomer(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

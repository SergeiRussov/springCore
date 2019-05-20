package org.russow.jdbc.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class ContactRepositoryImpl implements ContactRepository<Contact> {

    private JDBCUtils driver;

    @Override
    public List<Contact> getContactsFromId(int id) {
        final List<Contact> result = new ArrayList<>();

        Connection connection = driver.createConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQLContact.GET_CONTACTS_FROM_ID.QUERY)) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final Contact contact = new Contact();

                contact.setId(rs.getInt("id"));
                contact.setAddress(rs.getString("address"));
                contact.setPhone(rs.getLong("phone"));
                contact.setEmail(rs.getString("email"));

                result.add(contact);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            driver.closeConnection(connection);
        }

        return result;
    }

    enum SQLContact {

        GET_CONTACTS_FROM_ID("SELECT * FROM contacts WHERE id = ?");

        String QUERY;

        SQLContact(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

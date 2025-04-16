package com.example.bookshop.dao;

import com.example.bookshop.entity.BookInstance;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookInstanceDao {

    private final ConnectionDao daoConnection;

    public BookInstanceDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<BookInstance> findAll() {
        List<BookInstance> bookInstances = new ArrayList<>();
        String query = "SELECT * FROM instance";
        try (Connection connection = daoConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                BookInstance bookInstance = new BookInstance(
                        rs.getLong("instance_code"),
                        rs.getLong("ID_number_of_check"),
                        rs.getString("ISBN_book")
                );
                bookInstances.add(bookInstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookInstances;
    }


}

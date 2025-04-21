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
        String query = "SELECT instance_code, ID_number_of_check, I.ISBN_BOOK, B.Book_name AS Book_name FROM instance I INNER JOIN book B ON I.ISBN_book = B.ISBN";
        try (Connection connection = daoConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                BookInstance bookInstance = new BookInstance(
                        rs.getLong("instance_code"),
                        rs.getLong("ID_number_of_check"),
                        rs.getString("ISBN_book")
                );
                bookInstance.setBook_name(rs.getString("Book_name"));
                bookInstances.add(bookInstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookInstances;
    }


}

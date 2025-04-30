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
        String query = "SELECT instance_code, ID_number_of_check, I.ISBN_BOOK, B.Book_name AS Book_name FROM instance I LEFT JOIN book B ON I.ISBN_book = B.ISBN ORDER BY Book_name";
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

    public void saveBookInstance(BookInstance bookInstance) {
        String query = "INSERT INTO instance (ID_number_of_check, ISBN_book) VALUES (?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            if (bookInstance.getReceipt_id() != null) {
                preparedStatement.setLong(1, bookInstance.getReceipt_id());
            } else {
                preparedStatement.setNull(1, Types.BIGINT);
            }

            preparedStatement.setString(2, bookInstance.getISBN());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bookInstance.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving book instance", e);
        }
    }

    public void deleteBookInstance(Long id) {
        String query = "DELETE FROM instance WHERE instance_code = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public BookInstance findById(Long id) {
        String query = "SELECT * FROM instance  WHERE instance_code = ?";
        BookInstance bookInstance = null;

        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bookInstance = new BookInstance(
                        rs.getLong("instance_code"),
                        rs.getLong("ID_number_of_check"),
                        rs.getString("ISBN_book")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find book", e);
        }
        return bookInstance;
    }


    public void updateBookInstance(BookInstance bookInstance) {
        String query = "UPDATE instance SET ID_number_of_check = ?, ISBN_book = ? WHERE instance_code = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (bookInstance.getReceipt_id() != null) {
                preparedStatement.setLong(1, bookInstance.getReceipt_id());
            } else {
                preparedStatement.setNull(1, Types.BIGINT);
            }

            preparedStatement.setString(2, bookInstance.getISBN());
            preparedStatement.setLong(3, bookInstance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book instance", e);
        }
    }

}

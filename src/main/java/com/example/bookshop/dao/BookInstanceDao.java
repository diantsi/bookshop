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
    public void saveAll(List<BookInstance> instances) {
        String query = "INSERT INTO instance (ID_number_of_check, ISBN_book) VALUES (?, ?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (BookInstance bookInstance : instances) {
                if (bookInstance.getReceipt_id() != null) {
                    preparedStatement.setLong(1, bookInstance.getReceipt_id());
                } else {
                    preparedStatement.setNull(1, Types.BIGINT);
                }
                preparedStatement.setString(2, bookInstance.getISBN());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving book instances", e);
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


    public List<BookInstance> findByIds(List<Long> ids) {
        List<BookInstance> bookInstances = new ArrayList<>();
        String query = "SELECT * FROM instance WHERE instance_code IN (" + String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new)) + ")";

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
            throw new RuntimeException("Cannot find book instances", e);
        }
        return bookInstances;
    }

    public List<BookInstance> findAvailableInstances() {
        String query = "SELECT i.instance_code as id, i.ISBN_book as ISBN, b.Book_name " +
                "FROM instance i " +
                "LEFT JOIN book b ON i.ISBN_book = b.ISBN " +
                "WHERE i.ID_number_of_check IS NULL";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<BookInstance> instances = new ArrayList<>();
            while (rs.next()) {
                BookInstance instance = new BookInstance();
                instance.setId(rs.getLong("id"));
                instance.setISBN(rs.getString("ISBN"));
                instance.setBook_name(rs.getString("Book_name"));
                instances.add(instance);
            }
            return instances;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find available book instances", e);
        }
    }

    public void updateAll(List<BookInstance> instances) {
        String query = "UPDATE instance SET ID_number_of_check = ? WHERE instance_code = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (BookInstance instance : instances) {
                ps.setLong(1, instance.getReceipt_id());
                ps.setLong(2, instance.getId());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update book instances", e);
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


    public List<BookInstance> findBookInstancesByReceiptId(Long receiptId) {
        List<BookInstance> instances = new ArrayList<>();
        String query = "SELECT i.instance_code as id, i.ID_number_of_check as receipt_id, " +
                "b.ISBN, b.Book_name, i.ISBN_book " +
                "FROM instance i " +
                "INNER JOIN book b ON i.ISBN_book = b.ISBN " +
                "WHERE i.ID_number_of_check = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, receiptId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookInstance instance = new BookInstance();
                instance.setId(rs.getLong("id"));
                instance.setReceipt_id(rs.getLong("receipt_id"));
                instance.setISBN(rs.getString("ISBN"));
                instance.setBook_name(rs.getString("Book_name"));
                instances.add(instance);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get book instances for receipt", e);
        }
        return instances;
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

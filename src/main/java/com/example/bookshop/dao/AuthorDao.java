package com.example.bookshop.dao;

import com.example.bookshop.entity.Author;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDao {


    private final ConnectionDao daoConnection;

    public AuthorDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM author";
        try (Connection connection = daoConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Author author = new Author(
                rs.getLong("ID_author"),
                rs.getString("full_name")
                 );
                authors.add(author);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public void saveAuthor(Author author) {
        String query = "INSERT INTO author (full_name) VALUES (?)";
        try (Connection connection = daoConnection.getConnection();
             java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getFull_name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving author", e);
        }
    }

    public void updateAuthor(Author author) {
        String query = "UPDATE author SET full_name = ? WHERE ID_author = ?";
        try (Connection connection = daoConnection.getConnection();
             java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getFull_name());
            preparedStatement.setLong(2, author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating author", e);
        }
    }
    public void deleteAuthor(Long id) {
        String query = "DELETE FROM author WHERE ID_author = ?";
        try (Connection connection = daoConnection.getConnection();
             java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting author", e);
        }
    }




}

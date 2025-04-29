package com.example.bookshop.dao;

import com.example.bookshop.entity.Author;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                        rs.getLong("Id_author"),
                        rs.getString("Full_name")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find authors", e);
        }
        return authors;
    }

    public Author findById(Long id) {
        String query = "SELECT * FROM author WHERE Id_author = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Author(
                        rs.getLong("Id_author"),
                        rs.getString("Full_name")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find author by id", e);
        }
        return null;
    }

    public void save(Author author) {
        String query = "INSERT INTO author (Full_name) VALUES (?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getFull_name());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                author.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save author", e);
        }
    }

    public void update(Author author) {
        String query = "UPDATE author SET Full_name = ? WHERE Id_author = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, author.getFull_name());
            ps.setLong(2, author.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update author", e);
        }
    }

    public void delete(Long id) {
        if (hasBooks(id)) {
            throw new IllegalStateException("Cannot delete author with associated books");
        }
        String query = "DELETE FROM author WHERE Id_author = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete author", e);
        }
    }


    public boolean hasBooks(Long authorId) {
        String query = "SELECT COUNT(*) FROM book_author WHERE Id_author = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot check if author has books", e);
        }
        return false;
    }
}
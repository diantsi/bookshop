package com.example.bookshop.dao;

import com.example.bookshop.entity.Genre;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDao {

    private final ConnectionDao daoConnection;

    public GenreDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT Id_genre, Genre_name, Genre_description, Number_of_books FROM genre";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getLong("Id_genre"),
                        rs.getString("Genre_name"),
                        rs.getString("Genre_description"),
                        rs.getInt("Number_of_books")
                );
                genres.add(genre);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get genres", e);
        }

        return genres;
    }


    public void saveGenre(Genre genre) {
        String query = "INSERT INTO genre (Genre_name, Genre_description, Number_of_books) VALUES (?, ?, ?)";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, genre.getName());
            ps.setString(2, genre.getDescription());
            ps.setInt(3, genre.getNumberOfBooks());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save genre", e);
        }
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM genre WHERE Id_genre = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete genre", e);
        }
    }
}

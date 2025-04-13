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
        if (genre.getId() == null) {
            String query = "INSERT INTO genre (Genre_name, Genre_description, Number_of_books) VALUES (?, ?, ?)";
            try (Connection conn = daoConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, genre.getName());
                ps.setString(2, genre.getDescription());
                ps.setInt(3, genre.getNumberOfBooks());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        genre.setId(generatedKeys.getLong(1));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Cannot save genre", e);
            }
        } else {
            String query = "UPDATE genre SET Genre_name = ?, Genre_description = ?, Number_of_books = ? WHERE Id_genre = ?";
            try (Connection conn = daoConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, genre.getName());
                ps.setString(2, genre.getDescription());
                ps.setInt(3, genre.getNumberOfBooks());
                ps.setLong(4, genre.getId());
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Cannot update genre", e);
            }
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

    public Genre findById(Long id) {
        String query = "SELECT Id_genre, Genre_name, Genre_description, Number_of_books FROM genre WHERE Id_genre = ?";
        Genre genre = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                genre = new Genre(
                        rs.getLong("Id_genre"),
                        rs.getString("Genre_name"),
                        rs.getString("Genre_description"),
                        rs.getInt("Number_of_books")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find genre", e);
        }

        return genre;
    }


}

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
}

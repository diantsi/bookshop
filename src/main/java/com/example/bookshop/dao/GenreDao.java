package com.example.bookshop.dao;

import com.example.bookshop.dto.GenreDto;
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
        String query = "SELECT genre_name, genre_description, number_of_books FROM genre";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getString("genre_name"),
                        rs.getString("genre_description"),
                        rs.getInt("number_of_books")
                );
                genres.add(genre);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get genres", e);
        }

        return genres;
    }
}

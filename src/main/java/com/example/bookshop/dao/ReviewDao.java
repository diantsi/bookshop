package com.example.bookshop.dao;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao {

    private final ConnectionDao daoConnection;

    public ReviewDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    //`ordered_number` int(11) NOT NULL AUTO_INCREMENT,
//                                        `user_name` varchar(30) NOT NULL,
//                                        `user_email` varchar(30) NOT NULL,
//                                        `number_of_chars` int(11) NOT NULL,
//                                        `grade` int(11) NOT NULL,
//                                        `review_date` date NOT NULL,
//        `review_status` varchar(20) NOT NULL,
//                                        `ISBN_book` varchar(30) NOT NULL,
//                                        `answered_number` int(11) NULL,
//                                        `tab_number_of_worker` varchar(30) NULL,
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT ordered_number, user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, answered_number, tab_number_of_worker FROM review";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer orderedNumber = rs.getInt("ordered_number");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                Integer numberOfChars = rs.getInt("number_of_chars");
                String text = rs.getString("text");
                Integer grade = rs.getInt("grade");
                LocalDate date = rs.getDate("review_date").toLocalDate();
                String reviewStatus = rs.getString("review_status");
                String bookISBN = rs.getString("ISBN_book");
                Integer answeredNumber = rs.getInt("answered_number");
                String tabNumberOfWorker = rs.getString("tab_number_of_worker");

                Review review = new Review(
                        orderedNumber, userName, userEmail, numberOfChars, text,
                        grade, date, reviewStatus, bookISBN, answeredNumber, tabNumberOfWorker);

                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get reviews", e);
        }

        return reviews;
    }


    /*public void saveGenre(Genre genre) {
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
    }*/


}

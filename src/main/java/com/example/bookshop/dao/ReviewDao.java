package com.example.bookshop.dao;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import com.example.bookshop.entity.Worker;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDao {

    private final ConnectionDao daoConnection;

    public ReviewDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT ordered_number, user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, answered_number, tab_number_of_worker FROM review ORDER BY review_date";

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


    /*public boolean existsByTabNumber(String tabNumber) {
        List<Worker> workers = new ArrayList<>();
        String query = "SELECT 1 FROM worker WHERE Tab_number = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, tabNumber);
            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Check worker by Tab number failed", e);
        }
    }*/

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

    public void saveReview(Review review) {
        String query = "INSERT INTO review (user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, answered_number, tab_number_of_worker) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, review.getUserName());
            ps.setString(2, review.getUserEmail());
            ps.setInt(3, review.getNumberOfChars());
            if (review.getText() != null) {
                ps.setString(4, review.getText());
            } else {
                ps.setObject(4, null, Types.VARCHAR);
            }
            ps.setInt(5, review.getGrade());
            ps.setObject(6, review.getDate(), Types.DATE);
            ps.setString(7, review.getStatus());
            ps.setString(8, review.getBookISBN());
            if (review.getNumberOfAnswer() != null) {
                ps.setInt(9, review.getNumberOfAnswer());
            } else {
                ps.setObject(9, null, Types.INTEGER);
            }
            ps.setString(10, review.getTabNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save review", e);
        }

    }

    public void editReview(Review review) {
        String query = "UPDATE review SET user_name = ?, user_email = ?, number_of_chars = ?, text = ?, grade = ?, review_date = ?, review_status = ?, ISBN_book = ?, answered_number = ?, tab_number_of_worker = ? WHERE ordered_number = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, review.getUserName());
            ps.setString(2, review.getUserEmail());
            ps.setInt(3, review.getNumberOfChars());
            if (review.getText() != null) {
                ps.setString(4, review.getText());
            } else {
                ps.setObject(4, null, Types.VARCHAR);
            }
            ps.setInt(5, review.getGrade());
            ps.setObject(6, review.getDate(), Types.DATE);
            ps.setString(7, review.getStatus());
            ps.setString(8, review.getBookISBN());
            if (review.getNumberOfAnswer() != null) {
                ps.setInt(9, review.getNumberOfAnswer());
            } else {
                ps.setObject(9, null, Types.INTEGER);
            }
            ps.setString(10, review.getTabNumber());
            ps.setInt(11, review.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot edit review", e);
        }

    }


    public void deleteById(Integer id) {
        String query = "DELETE FROM review WHERE ordered_number = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete a review", e);
        }
    }

    //`ordered_number` int(11) NOT NULL AUTO_INCREMENT,
//                                        `user_name` varchar(30) NOT NULL,
//                                        `user_email` varchar(30) NOT NULL,
//                                        `number_of_chars` int(11) NOT NULL,
//                                        `text` varchar(500) NULL,
//                                        `grade` int(11) NOT NULL,
//                                        `review_date` date NOT NULL,
//            `review_status` varchar(20) NOT NULL,
//                                        `ISBN_book` varchar(30) NOT NULL,
//                                        `answered_number` int(11) NULL,
//                                        `tab_number_of_worker` varchar(30) NULL,
    public Review findById(Integer id) {
        String query = "SELECT ordered_number, user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, answered_number, tab_number_of_worker FROM review WHERE ordered_number = ?";
        Review review = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Integer idNum = rs.getInt("ordered_number");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                Integer numberOfChars = rs.getInt("number_of_chars");
                String text = rs.getString("text");
                Integer grade = rs.getInt("grade");
                LocalDate date = rs.getDate("review_date").toLocalDate();
                String status = rs.getString("review_status");
                String bookISBN = rs.getString("ISBN_book");
                Integer numberOfAnswer = rs.getInt("answered_number");
                String tabNumber = rs.getString("tab_number_of_worker");

                review = new Review(
                        idNum, userName, userEmail, numberOfChars,
                        text, grade, date, status, bookISBN,
                        numberOfAnswer, tabNumber);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find review", e);
        }

        return review;
    }

    public List<Review> findAllAnswers(Integer id) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT ordered_number, user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, answered_number, tab_number_of_worker FROM review WHERE answered_number =? ORDER BY review_date";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

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

}

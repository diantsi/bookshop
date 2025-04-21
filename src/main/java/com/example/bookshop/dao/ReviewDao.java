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
        String query = "INSERT INTO review (user_name, user_email, number_of_chars, text, grade, review_date, review_status, ISBN_book, tab_number_of_worker) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            //ps.setInt(9, 0);
            ps.setString(9, review.getTabNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save review", e);
        }

    }

    /*public void editWorker(Worker worker, String oldTabNumber) {
        if(worker.getPassword() != null) {}
        String query = "UPDATE worker SET Tab_number = ?, Surname = ?, First_name = ?, Middle_name = ?, Occupation = ?, Salary = ?, Start_working_date = ?, Date_of_birth = ?, Age = ?, City = ?, Street = ?, Building = ?, Flat = ?, `Index` = ?, Email_address = ?, password = ?, Phone_number = ? WHERE Tab_number = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, worker.getTabNumber());
            ps.setString(2, worker.getSurname());
            ps.setString(3, worker.getFirstName());
            if (worker.hasMiddleName()) {
                ps.setString(4, worker.getMiddleName());
            } else {
                ps.setObject(4, null, Types.VARCHAR);
            }
            ps.setString(5, worker.getOccupation());
            ps.setObject(6, worker.getSalary(), Types.DECIMAL);
            ps.setObject(7, worker.getStartWorkingDate(), Types.DATE);
            ps.setObject(8, worker.getDateOfBirth(), Types.DATE);
            ps.setInt(9, worker.calculateAge());
            ps.setString(10, worker.getCity());
            ps.setString(11, worker.getStreet());
            ps.setString(12, worker.getBuilding());
            if (worker.hasFlat()) {
                ps.setInt(13, worker.getFlat());
            } else {
                ps.setObject(13, null, Types.INTEGER);
            }
            ps.setInt(14, worker.getIndex());
            ps.setString(15, worker.getEmail());
            ps.setString(16, worker.getPassword());
            ps.setString(17, worker.getPhoneNumber());
            ps.setString(18, oldTabNumber);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot edit worker", e);
        }

    }

    public void editWorkerWithoutPassword(Worker worker, String oldTabNumber) {
        if(worker.getPassword() != null) {}
        String query = "UPDATE worker SET Tab_number = ?, Surname = ?, First_name = ?, Middle_name = ?, Occupation = ?, Salary = ?, Start_working_date = ?, Date_of_birth = ?, Age = ?, City = ?, Street = ?, Building = ?, Flat = ?, `Index` = ?, Email_address = ?, Phone_number = ? WHERE Tab_number = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, worker.getTabNumber());
            ps.setString(2, worker.getSurname());
            ps.setString(3, worker.getFirstName());
            if (worker.hasMiddleName()) {
                ps.setString(4, worker.getMiddleName());
            } else {
                ps.setObject(4, null, Types.VARCHAR);
            }
            ps.setString(5, worker.getOccupation());
            ps.setObject(6, worker.getSalary(), Types.DECIMAL);
            ps.setObject(7, worker.getStartWorkingDate(), Types.DATE);
            ps.setObject(8, worker.getDateOfBirth(), Types.DATE);
            ps.setInt(9, worker.calculateAge());
            ps.setString(10, worker.getCity());
            ps.setString(11, worker.getStreet());
            ps.setString(12, worker.getBuilding());
            if (worker.hasFlat()) {
                ps.setInt(13, worker.getFlat());
            } else {
                ps.setObject(13, null, Types.INTEGER);
            }
            ps.setInt(14, worker.getIndex());
            ps.setString(15, worker.getEmail());
            ps.setString(16, worker.getPhoneNumber());
            ps.setString(17, oldTabNumber);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot edit worker", e);
        }

    }*/

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

    /*public Worker findByTabNumber(String tabNumber) {
        String query = "SELECT Tab_number, Surname, First_name, Middle_name, Occupation, Salary, Start_working_date, Date_of_birth, Age, City, Street, Building, Flat, `Index`, Email_address, password, Phone_number FROM worker WHERE Tab_number = ?";
        Worker worker = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, tabNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String tabNum = rs.getString("Tab_number");
                String surname = rs.getString("Surname");
                String firstName = rs.getString("First_name");
                String middleName = rs.getString("Middle_name");
                String occupation = rs.getString("Occupation");
                BigDecimal salary = rs.getBigDecimal("Salary");
                LocalDate startDate = rs.getDate("Start_working_date").toLocalDate();
                LocalDate birthDate = rs.getDate("Date_of_birth").toLocalDate();
                Integer age = rs.getInt("Age");
                String city = rs.getString("City");
                String street = rs.getString("Street");
                String building = rs.getString("Building");
                Integer flat = rs.getInt("Flat");
                Integer index = rs.getInt("Index");
                String email = rs.getString("Email_address");
                String password = rs.getString("password");
                String phone = rs.getString("Phone_number");

                worker = new Worker(
                        tabNum, surname, firstName, middleName,
                        occupation, salary, startDate, birthDate, age,
                        city, street, building,
                        flat, index, email, password, phone);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find worker", e);
        }

        return worker;
    }

    public Optional<Worker> findByEmail(String emailToFind) {
        String query = "SELECT Tab_number, Surname, First_name, Middle_name, Occupation, Salary, Start_working_date, Date_of_birth, Age, City, Street, Building, Flat, `Index`, Email_address, password, Phone_number FROM worker WHERE Email_address = ?";
        Worker worker = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, emailToFind);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String tabNum = rs.getString("Tab_number");
                String surname = rs.getString("Surname");
                String firstName = rs.getString("First_name");
                String middleName = rs.getString("Middle_name");
                String occupation = rs.getString("Occupation");
                BigDecimal salary = rs.getBigDecimal("Salary");
                LocalDate startDate = rs.getDate("Start_working_date").toLocalDate();
                LocalDate birthDate = rs.getDate("Date_of_birth").toLocalDate();
                Integer age = rs.getInt("Age");
                String city = rs.getString("City");
                String street = rs.getString("Street");
                String building = rs.getString("Building");
                Integer flat = rs.getInt("Flat");
                Integer index = rs.getInt("Index");
                String email = rs.getString("Email_address");
                String password = rs.getString("password");
                String phone = rs.getString("Phone_number");

                worker = new Worker(
                        tabNum, surname, firstName, middleName,
                        occupation, salary, startDate, birthDate, age,
                        city, street, building,
                        flat, index, email, password, phone);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find worker", e);
        }

        return Optional.ofNullable(worker);
    }*/


}

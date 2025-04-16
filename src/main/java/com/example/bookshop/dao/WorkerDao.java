package com.example.bookshop.dao;

import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Worker;
import com.mysql.cj.xdevapi.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkerDao {
    private final ConnectionDao daoConnection;

    public WorkerDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Worker> findAll() {
        List<Worker> workers = new ArrayList<>();
        String query = "SELECT Tab_number, Surname, First_name, Occupation, Email_address FROM worker ORDER BY Tab_number";
//Long tabNumber, String surname, String firstName, String occupation, String email
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Worker worker = new Worker(
                        rs.getString("Tab_number"),
                        rs.getString("Surname"),
                        rs.getString("First_name"),
                        rs.getString("Occupation"),
                        rs.getString("Email_address")
                );
                workers.add(worker);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get workers", e);
        }

        return workers;
    }

    public boolean existsByTabNumber(String tabNumber) {
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
    }
//`Tab_number` varchar(30) NOT NULL,
//                                        `Surname` varchar(30) NOT NULL,
//                                        `First_name` varchar(30) NOT NULL,
//                                        `Middle_name` varchar(30) NULL,
//                                        `Occupation` varchar(32) NOT NULL,
//                                        `Salary` decimal(10,2) NOT NULL,
//                                        `Start_working_date` date NOT NULL,
//        `Date_of_birth` date NOT NULL,
//        `Age` int(11) NOT NULL,
//                                        `City` varchar(30) NOT NULL,
//                                        `Street` varchar(50) NOT NULL,
//                                        `Building` varchar(5) NOT NULL,
//                                        `Flat` int(11) NULL,
//                                        `Index` int(11) NOT NULL,
//                                        `Email_address` varchar(30) NOT NULL,
//                                        `password` varchar(255) NOT NULL, -- Increased length for hashed passwords
//                                        `Phone_number` varchar(13) NOT NULL,


    public void saveWorker(Worker worker) {
        String query = "INSERT INTO worker (Tab_number, Surname, First_name, Middle_name, Occupation, Salary, Start_working_date, Date_of_birth, Age, City, Street, Building, Flat, `Index`, Email_address, password, Phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save worker", e);
        }

    }

    public void editWorker(Worker worker, String oldTabNumber) {
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

    public void deleteByTabNo(String tabNumber) {
        String query = "DELETE FROM worker WHERE Tab_number = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, tabNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete a worker", e);
        }
    }

    public Worker findByTabNumber(String tabNumber) {
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

}

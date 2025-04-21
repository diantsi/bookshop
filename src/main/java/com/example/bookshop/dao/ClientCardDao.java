package com.example.bookshop.dao;

import com.example.bookshop.entity.ClientCard;
import com.example.bookshop.entity.Worker;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientCardDao {
    private final ConnectionDao daoConnection;

    public ClientCardDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }
    //    `ID_number` varchar(32) NOT NULL,
//        `Surname` varchar(30) NOT NULL,
//        `First_name` varchar(30) NOT NULL,
//        `Middle_name` varchar(30) NULL,
//        `Phone_number` varchar(13) NOT NULL,
//        `Registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//        `Date_of_birth` date NOT NULL,
//        `Age` int NOT NULL,
//        `Email_address` varchar(30) NOT NULL,
//        `Bonus_number` int NOT NULL,
//        PRIMARY KEY (`ID_number`),
//        UNIQUE KEY (`Phone_number`)
    public List<ClientCard> findAll() {
        List<ClientCard> clientCards = new ArrayList<>();
        String query = "SELECT ID_number, Surname, First_name, Phone_number, Bonus_number FROM client_card ORDER BY ID_number";
        //String idNumber, String surname, String firstName, String phoneNumber, Integer numberOfBonuses
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClientCard clientCard = new ClientCard(
                        rs.getString("ID_number"),
                        rs.getString("Surname"),
                        rs.getString("First_name"),
                        rs.getString("Phone_number"),
                        rs.getInt("Bonus_number")
                );
                clientCards.add(clientCard);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get client cards", e);
        }

        return clientCards;
    }

    public boolean existsByIdNumber(String idNumber) {
        List<ClientCard> clientCards = new ArrayList<>();
        String query = "SELECT 1 FROM client_card WHERE ID_number = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idNumber);
            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Check client card by ID number failed", e);
        }
    }


    //    `ID_number` varchar(32) NOT NULL,
//        `Surname` varchar(30) NOT NULL,
//        `First_name` varchar(30) NOT NULL,
//        `Middle_name` varchar(30) NULL,
//        `Phone_number` varchar(13) NOT NULL,
//        `Registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//        `Date_of_birth` date NOT NULL,
//        `Age` int NOT NULL,
//        `Email_address` varchar(30) NOT NULL,
//        `Bonus_number` int NOT NULL,
//        PRIMARY KEY (`ID_number`),
//        UNIQUE KEY (`Phone_number`)

    public void saveClientCard(ClientCard clientCard) {
        String query = "INSERT INTO client_card (ID_number, Surname, First_name, Middle_name, Phone_number, Date_of_birth, Age, Email_address, Bonus_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, clientCard.getIdNumber());
            ps.setString(2, clientCard.getSurname());
            ps.setString(3, clientCard.getFirstName());
            if (clientCard.getMiddleName() != null) {
                ps.setString(4, clientCard.getMiddleName());
            } else {
                ps.setObject(4, null, Types.VARCHAR);
            }
            ps.setString(5, clientCard.getPhoneNumber());
            //ps.setObject(7, clientCard.getRegistrationDate(), Types.DATE);
            ps.setObject(6, clientCard.getDateOfBirth(), Types.DATE);
            ps.setInt(7, clientCard.calculateAge());
            ps.setString(8, clientCard.getEmail());
            ps.setInt(9, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save client card", e);
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
    }*/

    public Optional<ClientCard> findByPhoneNumber(String phoneNumber) {
        String query = "SELECT ID_number, Surname, First_name, Middle_name, Phone_number, Registration_date, Date_of_birth, Age, Email_address, Bonus_number FROM client_card WHERE Phone_number = ?";
        ClientCard clientCard = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String idNum = rs.getString("ID_number");
                String surname = rs.getString("Surname");
                String firstName = rs.getString("First_name");
                String middleName = rs.getString("Middle_name");
                String phone = rs.getString("Phone_number");
                LocalDate registrationDate = rs.getDate("Registration_date").toLocalDate();
                LocalDate birthDate = rs.getDate("Date_of_birth").toLocalDate();
                Integer age = rs.getInt("Age");
                String email = rs.getString("Email_address");
                Integer bonuses = rs.getInt("Bonus_number");

                clientCard = new ClientCard(
                        idNum, surname, firstName, middleName,
                        phone, registrationDate, birthDate, age,
                        email, bonuses);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find client card", e);
        }

        return Optional.ofNullable(clientCard);
    }

}

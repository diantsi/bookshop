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

    public void editClientCard(ClientCard clientCard) {
        String query = "UPDATE client_card SET ID_number =?, Surname =?, First_name =?, Middle_name =?, Phone_number =?, Date_of_birth =?, Age =?, Email_address =?, Bonus_number =? WHERE ID_number = ?";
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
            ps.setObject(6, clientCard.getDateOfBirth(), Types.DATE);
            ps.setInt(7, clientCard.calculateAge());
            ps.setString(8, clientCard.getEmail());
            if (clientCard.getNumberOfBonuses() != null) {
                ps.setInt(9, clientCard.getNumberOfBonuses());
            } else {
                ps.setObject(9, 0, Types.INTEGER);
            }
            ps.setString(10, clientCard.getIdNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot edit client card", e);
        }
    }


    public void deleteById(String idNumber) {
        String query = "DELETE FROM client_card WHERE ID_number = ?";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, idNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete a clientcard", e);
        }
    }

    public ClientCard findById(String idNumber) {
        String query = "SELECT ID_number, Surname, First_name, Middle_name, Phone_number, Registration_date, Date_of_birth, Age, Email_address, Bonus_number FROM client_card WHERE ID_number = ?";
        ClientCard clientCard = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, idNumber);
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

        return clientCard;
    }

    public ClientCard findBySurname(String surnameGiven) {
        String query = "SELECT ID_number, Surname, First_name, Middle_name, Phone_number, Registration_date, Date_of_birth, Age, Email_address, Bonus_number FROM client_card WHERE Surname = ?";
        ClientCard clientCard = null;

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, surnameGiven);
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

        return clientCard;
    }


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


    public List<ClientCard> findAllWithReceiptCount() {
        String query = """
                    SELECT c.ID_number, c.Surname, c.First_name, c.Phone_number, c.Bonus_number,
                           COUNT(r.Id_number_of_check) AS Receipt_count
                    FROM client_card c
                    LEFT JOIN receipt r ON c.ID_number = r.ID_number_client
                    GROUP BY c.ID_number, c.Surname, c.First_name, c.Phone_number
                    ORDER BY c.ID_number
                """;
        List<ClientCard> clientCards = new ArrayList<>();

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
                clientCard.setNumberOfReceipts(rs.getInt("Receipt_count"));
                clientCards.add(clientCard);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot fetch client cards with receipt count", e);
        }

        return clientCards;
    }


    public List<ClientCard> findAllWithReceiptCountByPrompt(String prompt) {
        String query = """
                    SELECT c.ID_number, c.Surname, c.First_name, c.Phone_number, c.Bonus_number,
                           COUNT(r.Id_number_of_check) AS Receipt_count
                    FROM client_card c
                    LEFT JOIN receipt r ON c.ID_number = r.ID_number_client
                    WHERE ID_number LIKE ? OR Surname LIKE ? OR Phone_number LIKE ?
                    GROUP BY c.ID_number, c.Surname, c.First_name, c.Phone_number
                    ORDER BY c.ID_number
                """;
        List<ClientCard> clientCards = new ArrayList<>();

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + prompt + "%");
            ps.setString(2, "%" + prompt + "%");
            ps.setString(3, "%" + prompt + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ClientCard clientCard = new ClientCard(
                        rs.getString("ID_number"),
                        rs.getString("Surname"),
                        rs.getString("First_name"),
                        rs.getString("Phone_number"),
                        rs.getInt("Bonus_number")
                );
                clientCard.setNumberOfReceipts(rs.getInt("Receipt_count"));
                clientCards.add(clientCard);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find client cards with receipt count", e);
        }

        return clientCards;
    }

}

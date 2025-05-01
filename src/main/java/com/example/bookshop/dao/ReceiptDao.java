package com.example.bookshop.dao;

import com.example.bookshop.entity.Receipt;
import com.example.bookshop.entity.Translator;
import org.springframework.stereotype.Repository;
import com.example.bookshop.entity.BookInstance;

import java.sql.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class ReceiptDao {

    private final ConnectionDao daoConnection;

    public ReceiptDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        String query = "SELECT " +
                "r.Id_number_of_check, " +
                "r.Date_buy, " +
                "r.Sum_of_check, " +
                "r.User_bonus_number, " +
                "r.ID_number_client, " +
                "CONCAT(c.Surname, ' ', c.First_name) AS Client_full_name," +
                "r.Tab_number_worker, " +
                "CONCAT(w.Surname, ' ', w.First_name) AS Worker_full_name " +
                "FROM receipt r " +
                "INNER JOIN worker w ON r.Tab_number_worker = w.Tab_number " +
                "LEFT JOIN client_card c ON r.ID_number_client = c.ID_number " +
                "ORDER BY r.Date_buy desc ";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Receipt receipt = new Receipt(
                        rs.getLong("Id_number_of_check"),
                        rs.getTimestamp("Date_buy").toLocalDateTime(),
                        rs.getDouble("sum_of_check"),
                        rs.getInt("User_bonus_number"),
                        rs.getString("ID_number_client"),
                        rs.getString("Tab_number_worker")
                );
                receipt.setClient_full_name(rs.getString("Client_full_name"));
                receipt.setWorker_full_name(rs.getString("Worker_full_name"));
                receipts.add(receipt);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get receipts", e);
        }

        return receipts;
    }

    public void saveReceipt(Receipt receipt) {
        if (receipt.getId() == null) {
            String query = "INSERT INTO receipt (Date_buy, Sum_of_check, User_bonus_number, ID_number_client, Tab_number_worker) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = daoConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setObject(1, receipt.getTime());
                ps.setDouble(2, receipt.getTotalPrice());
                if (receipt.getBonuses() == null) {
                    ps.setNull(3, Types.INTEGER);
                } else {
                    ps.setInt(3, receipt.getBonuses());
                }
                if (receipt.getClient_id() == null || receipt.getClient_id().isEmpty()) {
                    ps.setNull(4, Types.VARCHAR);
                } else {
                    ps.setString(4, receipt.getClient_id());
                }
                ps.setString(5, receipt.getWorker_id());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        receipt.setId(generatedKeys.getLong(1));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Cannot save receipt", e);
            }
        }
    }

    public Receipt findById(Long id) {
        String query = "SELECT " +
                "r.Id_number_of_check, " +
                "r.Date_buy, " +
                "r.Sum_of_check, " +
                "r.User_bonus_number, " +
                "r.ID_number_client, " +
                "CONCAT(c.Surname, ' ', c.First_name) AS Client_full_name," +
                "r.Tab_number_worker, " +
                "CONCAT(w.Surname, ' ', w.First_name) AS Worker_full_name " +
                "FROM receipt r " +
                "INNER JOIN worker w ON r.Tab_number_worker = w.Tab_number " +
                "INNER JOIN client_card c ON r.ID_number_client = c.ID_number " +
                "WHERE r.Id_number_of_check = ? ";
        Receipt receipt = null;
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                receipt = new Receipt(
                        rs.getLong("Id_number_of_check"),
                        rs.getTimestamp("Date_buy").toLocalDateTime(),
                        rs.getDouble("Sum_of_check"),
                        rs.getInt("User_bonus_number"),
                        rs.getString("ID_number_client"),
                        rs.getString("Tab_number_worker")
                );
            }
            receipt.setClient_full_name(rs.getString("Client_full_name"));
            receipt.setWorker_full_name(rs.getString("Worker_full_name"));
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find receipt", e);
        }
        return receipt;
    }

    public void updateReceipt(Receipt receipt) {
        String query = "UPDATE receipt SET Date_buy = ?, Sum_of_check = ?, User_bonus_number = ?, ID_number_client = ?, Tab_number_worker = ? WHERE Id_number_of_check = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setObject(1, receipt.getTime());
            ps.setDouble(2, receipt.getTotalPrice());
            ps.setInt(3, receipt.getBonuses());
            ps.setString(4, receipt.getClient_id());
            ps.setString(5, receipt.getWorker_id());
            ps.setLong(6, receipt.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update receipt", e);
        }
    }

    public void deleteReceipt(Long id) {
        String query = "DELETE FROM receipt WHERE Id_number_of_check = ?";
        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete receipt", e);
        }
    }

}

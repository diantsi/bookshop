package com.example.bookshop.dao;

import com.example.bookshop.entity.Receipt;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ReceiptDao {

    private final ConnectionDao daoConnection;

    public ReceiptDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        String query = "SELECT Id_number_of_check, Date_buy, Sum_of_check, User_bonus_number, ID_number_client, Tab_number_worker  FROM receipt";

        try (Connection conn = daoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Receipt receipt = new Receipt(
                        rs.getLong("Id_number_of_check"),
                        rs.getTimestamp("Date_buy"),
                        rs.getDouble("sum_of_check"),
                        rs.getInt("User_bonus_number"),
                        rs.getString("ID_number_client"),
                        rs.getString("Tab_number_worker")
                );
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

                ps.setTimestamp(1, receipt.getTime());
                ps.setDouble(2, receipt.getTotalPrice());
                ps.setInt(3, receipt.getBonuses());
                ps.setString(4, receipt.getClient_id());
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

    public Receipt findReceiptById(Long id) {
        return null;
    }

}

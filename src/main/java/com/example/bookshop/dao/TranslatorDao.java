package com.example.bookshop.dao;

import com.example.bookshop.entity.Translator;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TranslatorDao {

    private final ConnectionDao daoConnection;

    public TranslatorDao(ConnectionDao daoConnection) {
        this.daoConnection = daoConnection;
    }

    public List<Translator> findAll() {
        List<Translator> translators = new ArrayList<>();
        String query = "SELECT * FROM translator ORDER BY Full_name";
        try (Connection connection = daoConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Translator translator = new Translator(
                        rs.getLong("Id_translator"),
                        rs.getString("Full_name")
                );
                translators.add(translator);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find translators", e);
        }
        return translators;
    }

    public Translator findById(Long id) {
        String query = "SELECT * FROM translator WHERE Id_translator = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Translator(
                        rs.getLong("Id_translator"),
                        rs.getString("Full_name")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find translator by id", e);
        }
        return null;
    }

    public void save(Translator translator) {
        String query = "INSERT INTO translator (Full_name) VALUES (?)";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, translator.getFull_name());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                translator.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save translator", e);
        }
    }

    public void update(Translator translator) {
        String query = "UPDATE translator SET Full_name = ? WHERE Id_translator = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, translator.getFull_name());
            ps.setLong(2, translator.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update translator", e);
        }
    }

    public void delete(Long id) {
        String query = "DELETE FROM translator WHERE Id_translator = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete translator", e);
        }
    }
    public boolean hasBooks(Long translatorId) {
        String query = "SELECT COUNT(*) FROM book_translator WHERE Id_translator = ?";
        try (Connection connection = daoConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, translatorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot check if translator has books", e);
        }
        return false;
    }
}
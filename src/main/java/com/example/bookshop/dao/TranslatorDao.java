package com.example.bookshop.dao;

import com.example.bookshop.entity.Translator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String query = "SELECT * FROM translator";
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
            e.printStackTrace();
        }
        return translators;
    }

    public void saveTranslator(Translator translator) {
        String query = "INSERT INTO translator (Full_name) VALUES (?)";
        try (Connection connection = daoConnection.getConnection();
             java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, translator.getFull_name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving translator", e);
        }
    }
}
package com.example.bookshop.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionDao {

    private final DaoProperties daoProperties;

    public ConnectionDao(DaoProperties daoProperties) {
        this.daoProperties = daoProperties;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    daoProperties.getUrl(),
                    daoProperties.getUser(),
                    daoProperties.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create DB connection", e);
        }
    }
}

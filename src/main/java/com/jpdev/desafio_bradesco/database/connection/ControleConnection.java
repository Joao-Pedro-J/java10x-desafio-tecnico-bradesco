package com.jpdev.desafio_bradesco.database.connection;

import com.jpdev.desafio_bradesco.utils.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ControlConnection implements DbConnection{
    @Override
    public Connection getConnection() throws SQLException {
        Properties env = EnvLoader.load();

        return DriverManager
                .getConnection(
                        env.getProperty("POSTGRES_CONTROLE_URL"),
                        env.getProperty("POSTGRES_CONTROLE_USER"),
                        env.getProperty("POSTGRES_CONTROLE_PASSWORD")
                );
    }
}

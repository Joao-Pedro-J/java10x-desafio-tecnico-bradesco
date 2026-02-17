package com.jpdev.desafio_bradesco.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnection {
    Connection getConnection() throws SQLException;
}

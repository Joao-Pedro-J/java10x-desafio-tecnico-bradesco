package com.jpdev.desafio_bradesco.database;

import com.jpdev.desafio_bradesco.utils.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
     private static Connection conn;

     public static Connection getConnection() throws SQLException {
         var env = EnvLoader.load();

         if (conn == null || conn.isClosed()) {
             conn = DriverManager.getConnection(
                             env.getProperty("POSTGRES_CONTROLE_URL"),
                             env.getProperty("POSTGRES_CONTROLE_USER"),
                             env.getProperty("POSTGRES_CONTROLE_PASSWORD")
             );
         }

         return conn;
     }
}

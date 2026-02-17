package com.jpdev.desafio_bradesco.database.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReplicadorDAO<T> {

    ArrayList<T> selectAll() throws SQLException;

    T selectById(Long id) throws SQLException;

    void insert(T tb) throws SQLException;

    void update(Long id, T tb) throws SQLException;

    void delete(Long id) throws SQLException;

}

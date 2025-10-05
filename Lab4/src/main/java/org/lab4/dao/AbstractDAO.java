package org.lab4.dao;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T> {
    void create(T entity) throws SQLException;
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(int id) throws SQLException;
}

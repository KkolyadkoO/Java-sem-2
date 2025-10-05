package org.lab4.dao;

import org.lab4.entity.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements AbstractDAO<Author> {
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Author author) throws SQLException {
        String sql = "INSERT INTO authors (full_name, birth_date) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, author.getFullName());
            stmt.setDate(2, Date.valueOf(author.getBirthDate()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Author getById(int id) throws SQLException {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Author(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate()
                );
            }
            return null;
        }
    }

    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM authors")) {
            while (rs.next()) {
                list.add(new Author(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate()
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Author author) throws SQLException {
        String sql = "UPDATE authors SET full_name=?, birth_date=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, author.getFullName());
            stmt.setDate(2, Date.valueOf(author.getBirthDate()));
            stmt.setInt(3, author.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM authors WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Доп. метод: авторы с более чем N книг
    public List<Author> getAuthorsWithMoreThanNBooks(int n) throws SQLException {
        String sql = """
                SELECT a.id, a.full_name, a.birth_date
                FROM authors a
                JOIN books b ON a.id = b.author_id
                GROUP BY a.id
                HAVING COUNT(b.id) > ?;
                """;
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate()
                ));
            }
        }
        return authors;
    }
}

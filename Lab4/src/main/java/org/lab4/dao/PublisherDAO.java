package org.lab4.dao;

import org.lab4.entity.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO implements AbstractDAO<Publisher> {
    private final Connection connection;

    public PublisherDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Publisher publisher) throws SQLException {
        String sql = "INSERT INTO publishers (name, country) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getCountry());
            stmt.executeUpdate();
        }
    }

    @Override
    public Publisher getById(int id) throws SQLException {
        String sql = "SELECT * FROM publishers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Publisher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country")
                );
            }
            return null;
        }
    }

    @Override
    public List<Publisher> getAll() throws SQLException {
        List<Publisher> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM publishers")) {
            while (rs.next()) {
                list.add(new Publisher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Publisher publisher) throws SQLException {
        String sql = "UPDATE publishers SET name = ?, country = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getCountry());
            stmt.setInt(3, publisher.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM publishers WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Publisher> getPublishersWithMoreThanNAuthors(int n) throws SQLException {
        String sql = """
            SELECT p.id, p.name, p.country
            FROM publishers p
            JOIN books b ON b.publisher_id = p.id
            JOIN authors a ON b.author_id = a.id
            GROUP BY p.id
            HAVING COUNT(DISTINCT a.id) > ?;
        """;

        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                publishers.add(new Publisher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country")
                ));
            }
        }
        return publishers;
    }
}

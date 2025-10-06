package org.lab4.dao;

import org.lab4.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements AbstractDAO<Book> {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author_id, publisher_id, publish_year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthorId());
            stmt.setInt(3, book.getPublisherId());
            stmt.setInt(4, book.getPublishYear());
            stmt.executeUpdate();
        }
    }

    @Override
    public Book getById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publish_year")
                );
            }
            return null;
        }
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publish_year")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, author_id=?, publisher_id=?, publish_year=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthorId());
            stmt.setInt(3, book.getPublisherId());
            stmt.setInt(4, book.getPublishYear());
            stmt.setInt(5, book.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Book> getBooksLast5Years() throws SQLException {
        int currentYear = java.time.Year.now().getValue();
        String sql = "SELECT * FROM books WHERE publish_year >= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, currentYear - 5);
            ResultSet rs = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publish_year")
                ));
            }
            return books;
        }
    }

    public List<Book> getBooksByAuthor(int authorId) throws SQLException {
        String sql = "SELECT * FROM books WHERE author_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publish_year")
                ));
            }
            return books;
        }
    }

    public void deleteBooksBeforeYear(int year) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE publish_year < ?")) {
            stmt.setInt(1, year);
            stmt.executeUpdate();
        }
    }
}

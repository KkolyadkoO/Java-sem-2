package org.lab6.repositories;

import org.lab6.db.DbContext;
import org.lab6.entity.UserEntity;

import java.sql.*;

public class UserRepository {

    public UserEntity findByLogin(String login) throws Exception {
        String sql = "SELECT * FROM users WHERE login=?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserEntity(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"));
            }
            return null;
        }
    }

    public void create(UserEntity user) throws Exception {
        String sql = "INSERT INTO users (login, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
        }
    }
}
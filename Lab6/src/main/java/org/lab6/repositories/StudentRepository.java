package org.lab6.repositories;

import org.lab6.db.DbContext;
import org.lab6.entity.StudentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public List<StudentEntity> findAll() throws Exception {
        List<StudentEntity> list = new ArrayList<>();
        String sql = "SELECT id, name, \"group\", avg_grade FROM students";

        try (Connection conn = DbContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new StudentEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("group"),
                        rs.getDouble("avg_grade")
                ));
            }
        }
        return list;
    }

    public StudentEntity findById(int id) throws Exception {
        String sql = "SELECT * FROM students WHERE id=?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new StudentEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("group"),
                        rs.getDouble("avg_grade")
                );
            }
            return null;
        }
    }

    public void create(StudentEntity s) throws Exception {
        String sql = "INSERT INTO students (name, \"group\", avg_grade) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getGroup());
            ps.setDouble(3, s.getAvgGrade());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s.setId(rs.getInt("id"));
            }
        }
    }

    public void update(StudentEntity s) throws Exception {
        String sql = "UPDATE students SET name=?, \"group\"=?, avg_grade=? WHERE id=?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getGroup());
            ps.setDouble(3, s.getAvgGrade());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

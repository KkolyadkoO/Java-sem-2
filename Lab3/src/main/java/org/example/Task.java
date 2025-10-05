package org.example;

import org.example.models.City;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class Task {
    public static void printAllCities() {
        String sql = "SELECT * FROM city LIMIT 100";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<City> cities = new ArrayList<>();

            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("countrycode"),
                        rs.getString("district"),
                        rs.getInt("population")
                );
                cities.add(city);
            }

            cities.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addTwoCities() {
        String sql = "INSERT INTO city (name, countrycode, district, population) VALUES " +
                "('TestCity1', 'USA', 'TestDistrict', 100000), " +
                "('TestCity2', 'FRA', 'TestDistrict', 200000)";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Добавлены 2 записи:");
            printAllCities();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCitiesWithPreparedStatement() {
        String sql = "INSERT INTO city (name, countrycode, district, population) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 5; i++) {
                pstmt.setString(1, "PrepCity" + i);
                pstmt.setString(2, "UKR");
                pstmt.setString(3, "Kyiv");
                pstmt.setInt(4, 50000 + i * 1000);
                pstmt.executeUpdate();
            }
            System.out.println("Добавлены 5 записей PreparedStatement:");
            printAllCities();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCitiesWithBatch() {
        String sql = "INSERT INTO city (name, countrycode, district, population) VALUES ('BatchCity', 'GER', 'Berlin', 300000)";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            for (int i = 1; i <= 4; i++) {
                stmt.addBatch(sql.replace("BatchCity", "BatchCity" + i));
            }

            stmt.executeBatch();
            System.out.println("Добавлены 4 записи через batch:");
            printAllCities();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMetaData() {
        try (Connection conn = DBConnection.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("DB Product: " + meta.getDatabaseProductName());
            System.out.println("DB Version: " + meta.getDatabaseProductVersion());
            System.out.println("User: " + meta.getUserName());

            try (ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    System.out.println("Table: " + tables.getString("TABLE_NAME"));
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM city LIMIT 1")) {
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnCount = rsMeta.getColumnCount();
                System.out.println("Columns in city:");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println(rsMeta.getColumnName(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printFrenchSpeakingCountries() {
        String sql = "SELECT c.name " +
                "FROM country c " +
                "JOIN сountrylanguage cl ON c.code = cl.countrycode " +
                "WHERE cl.language = 'French'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Страны, где говорят по-французски:");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

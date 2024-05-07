package ru.soroko.databases;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_authors (" +
                "id SERIAL PRIMARY KEY, " +
                "unique_name VARCHAR(50) NOT NULL, " +
                "registered_at DATE DEFAULT CURRENT_DATE NOT NULL, " +
                "is_active BOOLEAN DEFAULT TRUE NOT NULL)";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db_soroko",
                "soroko",
                "123"
        )) {
            try (Statement statement = connection.createStatement()) {
                // prepare statement another option
                // All queries except SELECT - creation, update, removal
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] insert(List<Author> authors) {
        /*String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (" + author.getUniqueName() + ", " + author.isActive() + ")";*/
        String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                for (Author author : authors) {
                    ps.setString(1, author.getUniqueName());
                    ps.setBoolean(2, author.isActive());
                    ps.addBatch();
                }
                return ps.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(Author author) {
        String updateSql = "UPDATE tb_authors SET is_active = ? " +
                " WHERE unique_name = ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setString(1, author.getUniqueName());
                ps.setBoolean(2, author.isActive());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author getByUniqueName(String uniqueName) {
        String selectSql = "SELECT id, unique_name, registered_at AS registered, is_active" +
                " FROM tb_authors WHERE unique_name = ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setString(1, uniqueName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    return author;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Author> allAuthors() {
        String selectSql = "SELECT id, unique_name, registered_at, is_active " +
                "FROM tb_authors WHERE is_active = true";
        try (Connection connection = C3P0Pool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(selectSql)){
                ResultSet resultSet = ps.executeQuery();
                List<Author> list = new ArrayList<>();
                while (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered_at", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    list.add(author);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

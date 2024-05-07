package ru.soroko.databases;

import java.sql.*;

public class NotesDao {
    public boolean create() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_notes()"
                + "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(120) NOT NULL, " +
                "text TEXT NOT NULL, " +
                "created_at TIMESTAMPTZ NOT NULL, " +
                "author_id INTEGER NOT NULL, " +
                "CONSTRAINT fk_author_notes " +
                "FOREIGN KEY (author_id) " +
                "REFERENCES tb_authors (id))";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

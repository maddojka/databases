package ru.soroko.databases;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotesDao {
    public boolean create() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_notes(" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(120) NOT NULL, " +
                "text TEXT NOT NULL, " +
                "created_at TIMESTAMPTZ CHECK (created_at < CURRENT_DATE), " +
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

    public int insert(Note note) {
        String insertSql = "INSERT INTO tb_notes (title, text, created_at, author_id) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, note.getTitle());
                ps.setString(2, note.getText());
                ps.setObject(3, note.getCreatedAt());
                ps.setInt(4, note.getAuthor().getId());
                ps.addBatch();
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Note getById(int id) {
        String selectSql = "SELECT *" +
                " FROM tb_notes WHERE id = ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    Note note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setTitle(resultSet.getString("title"));
                    note.setText(resultSet.getString("text"));
                    note.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    return note;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Note> getByAuthorId(Author author) {
        String selectSql = "SELECT *" +
                " FROM tb_notes WHERE author_id = 2";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Note> notes = new ArrayList<>();
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setTitle(resultSet.getString("title"));
                    note.setText(resultSet.getString("text"));
                    note.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    author.setId(resultSet.getInt("author_id"));
                    note.setAuthor(author);
                    notes.add(note);
                }
                return notes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Note> getByIdWithLimitAndOffset(Author author) {
        String selectSql = "SELECT * " +
                "FROM tb_notes " +
                "LIMIT 2 OFFSET 1";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Note> notes = new ArrayList<>();
                while (resultSet.next()) {
                    Note note = new Note();
                    Author newAuthor = new Author();
                    note.setId(resultSet.getInt("id"));
                    note.setTitle(resultSet.getString("title"));
                    note.setText(resultSet.getString("text"));
                    note.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    newAuthor.setId(resultSet.getInt("author_id"));
                    note.setAuthor(newAuthor);
                    notes.add(note);
                }
                return notes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
// 1. CHECK на created_at - дата в прошлом
// 2. INSERT tb_notes
// 3. SELECT tb_notes по идентификатору
// 4. SELECT tb_notes по идентификатору автора
// 5. SELECT tb_notes c LIMIT и OFFSET
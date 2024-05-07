package ru.soroko.databases;

import java.time.OffsetDateTime;
import java.util.List;

public class Databases {
    public static void main(String[] args) {
        System.out.println("Databases Lessons");

        Author author = new Author();
        author.setId(1);
        author.setUniqueName("aut001");
        Note note = new Note();
        note.setTitle("games");
        note.setText("info about games");
        note.setCreatedAt(OffsetDateTime.now());
        note.setAuthor(author);

        AuthorDao authorDao = new AuthorDao();
        authorDao.createTable();
        NotesDao notesDao = new NotesDao();
        notesDao.create();
        notesDao.insert(note);
        Note getNote = notesDao.getById(5);
        System.out.println(getNote);
        List<Note> notesByAuthorId = notesDao.getByAuthorId(author);
        System.out.println(notesByAuthorId);
        List<Note> notes = notesDao.getByIdWithLimitAndOffset();
        System.out.println(notes);
    }
}

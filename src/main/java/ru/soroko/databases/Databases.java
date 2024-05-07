package ru.soroko.databases;

public class Databases {
    public static void main(String[] args) {
        System.out.println("Databases Lessons");

        Author author = new Author();
        author.setUniqueName("aut001");
        Note note = new Note();


        AuthorDao authorDao = new AuthorDao();
        authorDao.createTable();
        NotesDao notesDao = new NotesDao();
        notesDao.create();
    }
}

package ru.soroko.databases;

import java.time.OffsetDateTime;

public class Note {
    private long id;
    private String title;
    private String text;
    private OffsetDateTime createdAt;
    private Author author; // author_id
}
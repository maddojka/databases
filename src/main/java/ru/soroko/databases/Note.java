package ru.soroko.databases;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Note {
    private long id;
    private String title;
    private String text;
    private OffsetDateTime createdAt;
    private Author author; // author_id
}

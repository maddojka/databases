package ru.soroko.databases;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Author {
    private int id;
    private String uniqueName;
    private LocalDate registeredAt;
    private boolean isActive = true;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", uniqueName='" + uniqueName + '\'' +
                ", registeredAt=" + registeredAt +
                ", isActive=" + isActive +
                '}';
    }
}

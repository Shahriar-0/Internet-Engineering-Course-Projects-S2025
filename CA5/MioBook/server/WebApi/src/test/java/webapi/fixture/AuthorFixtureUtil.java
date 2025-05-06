package webapi.fixture;

import domain.entities.author.Author;

import java.time.LocalDate;

public class AuthorFixtureUtil {

    public static final String NATIONALITY = "Persia";

    public static Author author(int index) {
        return Author.builder()
            .name(name(index))
            .penName(penName(index))
            .nationality(NATIONALITY)
            .born(born(index))
            .died(null)
            .build();
    }

    public static String name(int index) {
        return "AuthorName_" + index;
    }

    public static String penName(int index) {
        return "AuthorPenName_" + index;
    }

    public static LocalDate born(int index) {
        return LocalDate.of(1990, 1, 1).plusYears(index);
    }
}

package webapi.fixture;

import application.usecase.admin.author.AddAuthor;
import domain.entities.author.Author;
import domain.entities.user.Admin;
import webapi.views.author.AuthorView;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    public static Author author(int index, Admin admin) {
        Author author = author(index);
        author.setAdmin(admin);
        return author;
    }

    public static AddAuthor.AddAuthorData addAuthorData(int index) {
        AddAuthor.AddAuthorData data = new AddAuthor.AddAuthorData();
        data.setName(name(index));
        data.setPenName(penName(index));
        data.setNationality(NATIONALITY);
        data.setBorn(born(index).toString());
        data.setDied(null);
        return data;
    }

    public static AuthorView view(int index) {
        AuthorView view = new AuthorView();
        view.setName(name(index));
        view.setPenName(penName(index));
        view.setNationality(NATIONALITY);
        view.setBorn(born(index));
        view.setDied(null);
        view.setBookCount(0);
        return view;
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

    public static void assertion(Author a1, Author a2) {
        assertThat(a1.getName()).isEqualTo(a2.getName());
        assertThat(a1.getPenName()).isEqualTo(a2.getPenName());
        assertThat(a1.getNationality()).isEqualTo(a2.getNationality());
        assertThat(a1.getBorn()).isEqualTo(a2.getBorn());
        assertThat(a1.getDied()).isEqualTo(a2.getDied());
    }
}

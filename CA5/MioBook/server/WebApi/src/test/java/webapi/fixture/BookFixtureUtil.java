package webapi.fixture;

import application.usecase.admin.book.AddBook;
import domain.entities.book.Book;
import domain.entities.book.BookContent;
import webapi.views.book.BookView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookFixtureUtil {

    public static final String PUBLISHER = "BookPublisher";
    public static final long PRICE = 1000L;
    public static final String SYNOPSIS = "BookSynopsis";
    public static final String CONTENT = "BookContent";
    public static final String IMAGE_LINK = "BookImageLink";
    public static final String COVER_LINK = "BookCoverLink";
    public static final List<String> GENRES = Arrays.asList(
        "Cyberpunk",
        "Historical",
        "Magical Realism",
        "Psychological ",
        "Academic"
    );

    public static Book book(int index) {
        Book book = Book.builder()
            .title(title(index))
            .publisher(PUBLISHER)
            .publishedYear(publishedYear(index))
            .basePrice(PRICE)
            .synopsis(SYNOPSIS)
            .genres(GENRES)
            .imageLink(IMAGE_LINK)
            .coverLink(COVER_LINK)
            .dateAdded(dateAdded(index))
            .build();

        book.setContent(new BookContent(book, CONTENT));
        return book;
    }

    public static AddBook.AddBookData addBookData(int index) {
        return new AddBook.AddBookData(
            null,
            title(index),
            PUBLISHER,
            SYNOPSIS,
            CONTENT,
            publishedYear(index),
            PRICE,
            GENRES,
            IMAGE_LINK,
            COVER_LINK
        );
    }

    public static BookView view(int index) {
        BookView view = new BookView();
        view.setTitle(title(index));
        view.setPublisher(PUBLISHER);
        view.setGenres(GENRES);
        view.setYear(publishedYear(index));
        view.setPrice(PRICE);
        view.setSynopsis(SYNOPSIS);
        view.setCover(COVER_LINK);
        return view;
    }

    public static String title(int index) {
        return "BookTitle_" + index;
    }

    public static int publishedYear(int index) {
        return 2000 + index;
    }

    public static LocalDateTime dateAdded(int index) {
        return LocalDateTime.of(2020, 1, 1, 0, 0).plusYears(index);
    }

    public static void assertion(Book b1, Book b2) {
        assertThat(b1.getTitle()).isEqualTo(b2.getTitle());
        assertThat(b1.getPublisher()).isEqualTo(b2.getPublisher());
        assertThat(b1.getPublishedYear()).isEqualTo(b2.getPublishedYear());
        assertThat(b1.getBasePrice()).isEqualTo(b2.getBasePrice());
        assertThat(b1.getSynopsis()).isEqualTo(b2.getSynopsis());
        assertThat(b1.getGenres()).isEqualTo(b2.getGenres());
        assertThat(b1.getContent().getContent()).isEqualTo(b2.getContent().getContent());
        assertThat(b1.getImageLink()).isEqualTo(b2.getImageLink());
        assertThat(b1.getDateAdded()).isEqualTo(b2.getDateAdded());
        assertThat(b1.getCoverLink()).isEqualTo(b2.getCoverLink());
    }
}

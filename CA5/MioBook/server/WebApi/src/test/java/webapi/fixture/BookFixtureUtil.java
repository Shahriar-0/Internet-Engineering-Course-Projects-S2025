package webapi.fixture;

import domain.entities.book.Book;
import domain.entities.book.BookContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static String title(int index) {
        return "BookTitle_" + index;
    }

    public static int publishedYear(int index) {
        return 2000 + index;
    }

    public static LocalDateTime dateAdded(int index) {
        return LocalDateTime.of(2020, 1, 1, 0, 0).plusYears(index);
    }
}

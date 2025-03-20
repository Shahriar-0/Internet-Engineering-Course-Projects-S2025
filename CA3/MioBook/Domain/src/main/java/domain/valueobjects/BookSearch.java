package domain.valueobjects;

import domain.entities.Book;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class BookSearch {

	private final List<Book> books;
    private final String query;

    public BookSearch(List<Book> books, Map<String, String> params) {
        this.books = books;
        if (params == null || params.size() == 0)
            this.query = "Show all books";
        else if (params.size() == 1) // don't show type and get the only param
            this.query = params.values().iterator().next();
        else
            this.query = params.toString();
    }

    public String getQuery() {
        return query;
    }
}

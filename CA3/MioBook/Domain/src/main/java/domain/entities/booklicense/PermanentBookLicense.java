package domain.entities.booklicense;

import domain.entities.book.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PermanentBookLicense extends BookLicense {
    public PermanentBookLicense(Book book) {
        super(book, book.getPrice());
    }
}

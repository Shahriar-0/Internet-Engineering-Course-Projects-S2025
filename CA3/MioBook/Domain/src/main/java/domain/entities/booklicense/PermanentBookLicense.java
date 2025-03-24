package domain.entities.booklicense;

import domain.entities.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PermanentBookLicense extends BookLicense {
    public PermanentBookLicense(Long id, Book book) {
        super(id, book, book.getPrice());
    }
}

package domain.entities.booklicense;

import domain.entities.Book;
import domain.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public abstract class BookLicense extends DomainEntity<Long> {
    protected Book book;
    protected long price;
    protected LocalDateTime purchaseDate;
    public Long getId() {
        return super.getKey();
    }

    public BookLicense(Long id, Book book, long price) {
        super(id);
        this.book = book;
        this.price = price;
        purchaseDate = null;
    }

    public boolean isValid() {
        return purchaseDate != null;
    }
}

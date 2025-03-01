package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import domain.entities.Book;
import lombok.Getter;

@Getter
public class CustomerBook {

    @JsonUnwrapped
    private final Book book;

    private final Boolean isBorrowed;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private final int borrowDays;

    @JsonIgnore
    private final long price;

    @JsonIgnore
    private transient final int MAXIMUM_BORROW_DAYS = 10;

    public CustomerBook(Book book, int borrowDays) {
        if (borrowDays > MAXIMUM_BORROW_DAYS)
            throw new RuntimeException("Borrow days cannot exceed " + MAXIMUM_BORROW_DAYS);
        if (borrowDays < 0)
            throw new RuntimeException("Borrow days cannot be negative");

        this.book = book;
        this.isBorrowed = borrowDays > 0;
        this.borrowDays = borrowDays;
        this.price = book.getPrice() * borrowDays / MAXIMUM_BORROW_DAYS;
    }

    public CustomerBook(Book book) {
        this.book = book;
        this.isBorrowed = false;
        this.borrowDays = 0;
        this.price = book.getPrice();
    }

    @JsonProperty("finalPrice")
    public long getFinalPrice() {
        return price;
    }

    @JsonIgnore
    public int getMAXIMUM_BORROW_DAYS() {
        return MAXIMUM_BORROW_DAYS;
	}
}

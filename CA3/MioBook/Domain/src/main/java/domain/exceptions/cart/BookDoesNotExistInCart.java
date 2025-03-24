package domain.exceptions.cart;

import domain.exceptions.DomainException;

public class BookDoesNotExistInCart extends DomainException {

    public BookDoesNotExistInCart(String bookTitle) {
        super(createMessage(bookTitle));
    }

    private static String createMessage(String title) {
        return "Book with title '" + title + "' is not in cart!";
    }
}

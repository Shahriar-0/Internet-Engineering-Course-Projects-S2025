package domain.exceptions.cart;

import domain.exceptions.DomainException;

public class BookAlreadyInCart extends DomainException {

    public BookAlreadyInCart(String bookTitle) {
        super(createMessage(bookTitle));
    }

    private static String createMessage(String title) {
        return "Book with title '" + title + "' is already in cart!";
    }
}

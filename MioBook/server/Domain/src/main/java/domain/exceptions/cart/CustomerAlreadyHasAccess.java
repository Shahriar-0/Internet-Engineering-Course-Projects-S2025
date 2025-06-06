package domain.exceptions.cart;

import domain.exceptions.DomainException;

public class CustomerAlreadyHasAccess extends DomainException {

    public CustomerAlreadyHasAccess(String bookTitle) {
        super(createMessage(bookTitle));
    }

    private static String createMessage(String title) {
        return "Book with title '" + title + "' has already been bought!";
    }
}

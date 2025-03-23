package webapi.views.customer;

import domain.valueobjects.PurchasedBooks;

import java.util.List;

public record PurchasedBooksView(
    List<CustomerBookView> books
) {
    public PurchasedBooksView(PurchasedBooks books) {
        this(books.getBooks().stream().map(CustomerBookView::new).toList());
    }
}

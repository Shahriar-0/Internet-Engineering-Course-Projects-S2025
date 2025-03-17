package webapi.views.customer;

import java.util.List;

import domain.valueobjects.PurchasedBooks;

public record PurchasedBooksView(
    List<CustomerBookView> books
) {
    public PurchasedBooksView(PurchasedBooks books) {
        this(books.getBooks().stream().map(CustomerBookView::new).toList());
    }
}

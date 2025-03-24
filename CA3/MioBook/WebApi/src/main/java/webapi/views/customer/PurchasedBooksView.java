package webapi.views.customer;

import domain.entities.booklicense.BookLicense;

import java.util.List;

public record PurchasedBooksView(
    List<CustomerBookView> books
) {
    public PurchasedBooksView(List<BookLicense> license) {
        this(license.stream().map(CustomerBookView::new).toList());
    }
}

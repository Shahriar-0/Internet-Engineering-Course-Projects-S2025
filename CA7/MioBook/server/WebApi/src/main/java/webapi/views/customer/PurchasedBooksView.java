package webapi.views.customer;

import domain.entities.booklicense.BookLicense;

import java.util.List;

public record PurchasedBooksView(
    List<CustomerBookView> books
) {
    public static PurchasedBooksView create(List<BookLicense> licenses) {
        return new PurchasedBooksView(licenses.stream().map(CustomerBookView::new).toList());
    }
}

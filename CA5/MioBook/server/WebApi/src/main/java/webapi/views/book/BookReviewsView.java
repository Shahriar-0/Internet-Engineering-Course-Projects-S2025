package webapi.views.book;

import domain.entities.book.Review;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record BookReviewsView(
    Integer rating,
    String comment,
    String customer,
    LocalDateTime date
) {
    public BookReviewsView(Review review) {
        this(
            review.getRating(),
            review.getComment(),
            review.getCustomer().getUsername(),
            review.getDateTime()
        );
    }

    public static Page<BookReviewsView> mapToView(Page<Review> reviewPage) {
        return reviewPage.map(BookReviewsView::new);
    }
}

package webapi.views.book;

import domain.entities.book.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewsView {
    Integer rating;
    String comment;
    String customer;
    LocalDateTime date;

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

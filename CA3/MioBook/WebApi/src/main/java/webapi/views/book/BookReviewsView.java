package webapi.views.book;

import application.pagination.Page;
import domain.entities.book.Review;

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
            review.getCustomerName(),
            review.getDateTime()
        );
    }

    public static Page<BookReviewsView> mapToView(Page<Review> reviewPage) {
        return Page
            .<BookReviewsView>builder()
            .list(reviewPage.getList().stream().map(BookReviewsView::new).toList())
            .pageNumber(reviewPage.getPageNumber())
            .pageSize(reviewPage.getPageSize())
            .totalPageNumber(reviewPage.getTotalPageNumber())
            .totalDataSize(reviewPage.getTotalDataSize())
            .build();
    }
}

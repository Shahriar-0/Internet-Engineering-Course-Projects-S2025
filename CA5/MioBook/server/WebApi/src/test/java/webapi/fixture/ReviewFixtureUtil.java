package webapi.fixture;

import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;

import java.time.LocalDateTime;

import static domain.entities.book.Review.MAX_RATING_NUMBER;
import static domain.entities.book.Review.MIN_RATING_NUMBER;

public class ReviewFixtureUtil {

    public static Review review(int index) {
        return Review.builder()
            .rating(rating(index))
            .comment(comment(index))
            .dateTime(dateTime(index))
            .build();
    }

    public static Review review(int index, Book book, Customer customer) {
        Review review = review(index);
        review.setBook(book);
        review.setCustomer(customer);
        return review;
    }

    public static int rating(int index) {
        return (index % MAX_RATING_NUMBER) + MIN_RATING_NUMBER;
    }

    public static String comment(int index) {
        return "Comment_" + index;
    }

    public static LocalDateTime dateTime(int index) {
        return LocalDateTime.of(2015, 1, 1, 0, 0).minusMonths(index);
    }
}

package webapi.fixture;

import application.usecase.customer.book.AddReview;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;
import webapi.views.book.BookReviewsView;

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

    public static BookReviewsView view(int index) {
        BookReviewsView view = new BookReviewsView();
        view.setRating(rating(index));
        view.setComment(comment(index));
        view.setDate(dateTime(index));
        return view;
    }

    public static BookReviewsView view(int index, String customer) {
        BookReviewsView view = view(index);
        view.setCustomer(customer);
        return view;
    }

    public static AddReview.AddReviewData addReviewData(int index) {
        AddReview.AddReviewData data = new AddReview.AddReviewData();
        data.setRating(rating(index));
        data.setComment(comment(index));
        return data;
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

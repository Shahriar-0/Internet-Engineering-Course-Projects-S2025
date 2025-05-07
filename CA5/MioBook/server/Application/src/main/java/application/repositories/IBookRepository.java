package application.repositories;

import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends IBaseRepository<Book> {
	Page<Book> filter(GetBook.BookFilter filter);
	Page<Review> filterReview(String title, GetBookReviews.ReviewFilter filter);
    List<String> getGenres();
    Optional<Book> findByTitle(String title);
    Review upsertReview(Review review, Book book, Customer customer);
}

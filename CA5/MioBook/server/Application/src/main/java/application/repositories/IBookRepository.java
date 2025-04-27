package application.repositories;

import application.pagination.Page;
import application.result.Result;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends IBaseRepository<Book> {
	Page<Book> filter(GetBook.BookFilter filter);
	Result<Page<Review>> filterReview(String title, GetBookReviews.ReviewFilter filter);
    List<String> getGenres();
    Optional<Book> findByTitle(String title);
}

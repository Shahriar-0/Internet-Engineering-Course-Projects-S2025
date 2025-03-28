package application.repositories;

import application.pagination.Page;
import application.result.Result;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;

public interface IBookRepository extends IBaseRepository<String, Book> {
	Page<Book> filter(GetBook.BookFilter filter);
	Result<Page<Review>> filterReview(String title, GetBookReviews.ReviewFilter filter);
}

package application.repositories;

import application.pagination.Page;
import application.result.Result;
import application.usecase.user.GetBookReviewsUseCase;
import application.usecase.user.GetBookUseCase;
import domain.entities.Book;
import domain.valueobjects.BookSearch;
import domain.valueobjects.Review;

import java.util.Map;

public interface IBookRepository extends IBaseRepository<String, Book> {
	Result<BookSearch> search(Map<String, String> params);
	Page<Book> filter(GetBookUseCase.BookFilter filter);
	Page<Review> filter(Book book, GetBookReviewsUseCase.ReviewFilter filter);
}

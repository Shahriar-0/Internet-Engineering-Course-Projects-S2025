package application.repositories;

import application.page.Page;
import application.result.Result;
import application.uscase.user.GetBookUseCase;
import domain.entities.Book;
import domain.valueobjects.BookSearch;
import java.util.Map;

public interface IBookRepository extends IBaseRepository<String, Book> {
	Result<BookSearch> search(Map<String, String> params);
	Page<Book> filter(GetBookUseCase.BookFilter filter);
}

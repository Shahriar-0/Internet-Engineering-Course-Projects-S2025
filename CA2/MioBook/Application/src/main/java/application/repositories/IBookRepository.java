package application.repositories;

import application.result.Result;
import domain.entities.Book;
import domain.valueobjects.BookSearch;
import java.util.Map;

public interface IBookRepository extends IBaseRepository<String, Book> {
	Result<BookSearch> search(Map<String, String> params);
}

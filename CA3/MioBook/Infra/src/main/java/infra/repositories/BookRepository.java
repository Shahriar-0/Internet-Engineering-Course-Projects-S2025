package infra.repositories;

import application.repositories.IBookRepository;
import application.result.Result;
import domain.entities.Book;
import domain.valueobjects.BookSearch;
import java.util.List;
import java.util.Map;

public class BookRepository extends BaseRepository<String, Book> implements IBookRepository {

	@Override
	protected Class<Book> getEntityClassType() {
		return Book.class;
	}

	@Override
	protected Book copyOf(Book persistedEntity) {
		return Book
			.builder()
			.key(persistedEntity.getTitle())
			.author(persistedEntity.getAuthor())
			.publisher(persistedEntity.getPublisher())
			.year(persistedEntity.getYear())
			.price(persistedEntity.getPrice())
			.synopsis(persistedEntity.getSynopsis())
			.content(persistedEntity.getContent())
			.genres(persistedEntity.getGenres())
			.build();
	}

	@Override
	public Result<BookSearch> search(Map<String, String> params) {
		List<Book> books = null;
        if (params.containsKey("title"))
            books = books == null ? searchTitle(params.get("title")) : filterTitle(books, params.get("title"));
        if (params.containsKey("username"))
            books = books == null ? searchName(params.get("username")) : filterName(books, params.get("username"));
        if (params.containsKey("genre"))
            books = books == null ? searchGenre(params.get("genre")) : filterGenre(books, params.get("genre"));
        if (params.containsKey("from") && params.containsKey("to")){
            int from = Integer.parseInt(params.get("from"));
            int to = Integer.parseInt(params.get("to"));
            books = books == null ? searchYear(from, to) : filterYear(books, from, to);
        }

        return Result.success(new BookSearch(books, params));
	}

	private List<Book> searchTitle(String title) {
        // For this search only we can use the keys in hash table
        return map.keySet().stream().filter(key -> key.contains(title)).map(map::get).toList();
    }

    private static List<Book> filterTitle(List<Book> books, String title) {
        return books.stream().filter(book -> book.getTitle().contains(title)).toList();
    }

    private List<Book> searchName(String name) {
        return map.values().stream().filter(book -> book.getAuthor().getName().contains(name)).toList();
    }

    private static List<Book> filterName(List<Book> books, String name) {
        return books.stream().filter(book -> book.getAuthor().getName().contains(name)).toList();
    }

    private List<Book> searchGenre(String genre) {
        return map.values().stream().filter(book -> book.getGenres().contains(genre)).toList();
    }

    private static List<Book> filterGenre(List<Book> books, String genre) {
        return books.stream().filter(book -> book.getGenres().contains(genre)).toList();
    }

    private List<Book> searchYear(int from, int to) {
        return map.values().stream().filter(book -> book.getYear() >= from && book.getYear() <= to).toList();
    }

    private static List<Book> filterYear(List<Book> books, int from, int to) {
        return books.stream().filter(book -> book.getYear() >= from && book.getYear() <= to).toList();
    }
}

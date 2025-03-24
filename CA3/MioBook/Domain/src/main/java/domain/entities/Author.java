package domain.entities;

import domain.entities.book.Book;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {

	private String penName;
	private String nationality;
	private LocalDate born;
	private LocalDate died;
	private final List<Book> books = new ArrayList<>();
	public String getName() {
		return super.getKey();
	}

	public void addBook(Book book) {
		assert book.getAuthor().getKey().equals(key);
		books.add(book);
	}
}

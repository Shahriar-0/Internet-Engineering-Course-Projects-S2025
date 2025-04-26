package domain.entities.author;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {

	private String penName;
	private String nationality;
	private LocalDate born;

	@Builder.Default
	private LocalDate died = null;

	@Builder.Default
	private List<Book> books = new ArrayList<>();

	public String getName() {
		return super.getKey();
	}

	public static Author createAliveAuthor(String name, String penName, String nationality, LocalDate born) {
		return Author
			.builder()
			.key(name)
			.penName(penName)
			.nationality(nationality)
			.born(born)
			.build();
	}

	public static Author createDeadAuthor(String name, String penName, String nationality, LocalDate born, LocalDate died) {
		return Author
			.builder()
			.key(name)
			.penName(penName)
			.nationality(nationality)
			.born(born)
			.died(died)
			.build();
	}

	public void addBook(Book book) {
		assert book.getAuthor().getKey().equals(key);
		books.add(book);
	}

	public int getBookCount() {
		return books.size();
	}
}

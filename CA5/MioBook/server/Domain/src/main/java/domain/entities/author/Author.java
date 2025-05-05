package domain.entities.author;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Author extends DomainEntity {

    private String name;
	private String penName;
	private String nationality;
	private LocalDate born;
	private LocalDate died;

	@Builder.Default
	private List<Book> books = new ArrayList<>();

	public static Author createAliveAuthor(String name, String penName, String nationality, LocalDate born) {
		return Author
			.builder()
			.name(name)
			.penName(penName)
			.nationality(nationality)
			.born(born)
			.build();
	}

	public static Author createDeadAuthor(String name, String penName, String nationality, LocalDate born, LocalDate died) {
		return Author
			.builder()
			.name(name)
			.penName(penName)
			.nationality(nationality)
			.born(born)
			.died(died)
			.build();
	}

	public void addBook(Book book) {
		assert book.getAuthor().getId().equals(id);
		books.add(book);
	}

	public int getBookCount() {
		return books.size();
	}
}

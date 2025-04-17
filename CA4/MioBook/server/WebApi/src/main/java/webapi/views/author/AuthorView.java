package webapi.views.author;

import java.time.LocalDate;
import java.util.List;

import domain.entities.author.Author;

public record AuthorView(
	String name,
	String penName,
	String nationality,
	LocalDate born,
	LocalDate died,
	int bookCount
) {
	public AuthorView(Author entity) {
		this(
			entity.getName(),
			entity.getPenName(),
			entity.getNationality(),
			entity.getBorn(),
			entity.getDied(),
			entity.getBookCount()
		);
	}

	public static List<AuthorView> mapToView(List<Author> authors) {
		return authors.stream().map(AuthorView::new).toList();
	}
}

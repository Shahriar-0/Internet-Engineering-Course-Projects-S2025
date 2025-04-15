package webapi.views.author;

import java.time.LocalDate;

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
}

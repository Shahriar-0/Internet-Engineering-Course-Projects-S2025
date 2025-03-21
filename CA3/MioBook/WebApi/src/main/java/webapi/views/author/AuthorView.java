package webapi.views.author;

import domain.entities.Author;

import java.time.LocalDate;

public record AuthorView(
	String name,
	String penName,
	String nationality,
	LocalDate born,
	LocalDate died
) {
	public AuthorView(Author entity) {
		this(
			entity.getName(),
			entity.getPenName(),
			entity.getNationality(),
			entity.getBorn(),
			entity.getDied()
		);
	}
}

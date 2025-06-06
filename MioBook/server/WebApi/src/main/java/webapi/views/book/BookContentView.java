package webapi.views.book;

import domain.entities.book.BookContent;

public record BookContentView(String content, String title, String authorName) {
	public BookContentView(BookContent entity) {
		this(entity.getContent(), entity.getBook().getTitle(), entity.getBook().getAuthor().getName());
	}
}

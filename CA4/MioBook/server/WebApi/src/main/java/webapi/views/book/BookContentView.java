package webapi.views.book;

import domain.entities.book.BookContent;

public record BookContentView(String content, String title) {
	public BookContentView(BookContent entity) {
		this(entity.getContent(), entity.getTitle());
	}
}

package webapi.views.book;

import domain.valueobjects.BookContent;

public record BookContentView(String content, String title) {
	public BookContentView(BookContent entity) {
		this(entity.content(), entity.title());
	}
}

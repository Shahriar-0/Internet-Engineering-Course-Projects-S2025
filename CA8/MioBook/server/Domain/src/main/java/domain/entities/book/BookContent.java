package domain.entities.book;

import domain.entities.DomainEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookContent extends DomainEntity {

	private Book book;
    private String content;

	public BookContent(Book book, String content) {
		this.book = book;
		this.content = content;
	}
}

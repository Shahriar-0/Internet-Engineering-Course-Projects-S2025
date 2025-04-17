package domain.entities.book;

import domain.entities.DomainEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookContent extends DomainEntity<String> {

	private String content;
	private String authorName;

	public String getTitle() {
		return key;
	}

	public BookContent(String title, String content, String authorName) {
		super(title);
		this.content = content;
		this.authorName = authorName;
	}
}

package domain.entities;

import java.util.List;

import domain.valueobjects.BookContent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Book extends DomainEntity<String> {

	private Author author;
	private String publisher;
	private String year; // Published year
	private long price;
	private String synopsis;
	private BookContent content;
	private List<String> genres;

	public String getTitle() {
		return super.getKey();
	}

	public Book(
		String title,
		Author author,
		String publisher,
		String year,
		long price,
		String synopsis,
		BookContent content,
		List<String> genres
	) {
		super(title);
		this.author = author;
		this.publisher = publisher;
		this.year = year;
		this.price = price;
		this.synopsis = synopsis;
		this.content = content;
		this.genres = genres;
	}
}

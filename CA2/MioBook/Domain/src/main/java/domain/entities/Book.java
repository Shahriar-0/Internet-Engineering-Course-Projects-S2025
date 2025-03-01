package domain.entities;

import java.util.ArrayList;
import java.util.List;

import domain.valueobjects.BookContent;
import domain.valueobjects.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Book extends DomainEntity<String> {

	private Author author;
	private String publisher;
	private int year; // published year
	private long price; // in cents
	private String synopsis;
	private BookContent content;
	private List<String> genres;

	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	public String getTitle() {
		return super.getKey();
	}

	public void addReview(Review review) {
		reviews.add(review);
	}
}

package domain.entities;

import domain.valueobjects.BookContent;
import domain.valueobjects.BookReviews;
import domain.valueobjects.Review;
import java.util.List;
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
	private List<String> genres;

	private BookContent content;

	@Builder.Default
	private BookReviews reviews = new BookReviews();

	@Override
	public String getKey() {
		return super.getKey();
	}

	public String getTitle() {
		return super.getKey();
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public float getAverageRating() {
		return reviews.getAverageRating();
	}

	public String getAuthorName() {
		return author != null ? author.getName() : null;
	}
}

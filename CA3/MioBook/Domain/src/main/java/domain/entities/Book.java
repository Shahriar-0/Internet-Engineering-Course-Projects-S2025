package domain.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.valueobjects.BookContent;
import domain.valueobjects.BookReviews;
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
	private List<String> genres;

	@JsonIgnore
	private BookContent content;

    @Builder.Default
    @JsonIgnore
    private BookReviews reviews = new BookReviews();

	@Override
	@JsonIgnore
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

	@JsonProperty("author")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }
}

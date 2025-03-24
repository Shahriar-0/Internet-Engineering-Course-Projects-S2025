package domain.entities.book;

import domain.entities.Author;
import domain.entities.DomainEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class Book extends DomainEntity<String> {

	private Author author;
	private String publisher;
	private int publishedYear;
	private long basePrice; // in cents
	private String synopsis;
	private final List<String> genres;
	private final BookContent content;
	private final List<Review> reviews = new ArrayList<>();
	public String getTitle() {
		return super.getKey();
	}

    public Book(String title, Author author, String publisher, int publishedYear, long basePrice, String synopsis, List<String> genres, String content) {
        super(title);
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.basePrice = basePrice;
        this.synopsis = synopsis;
        this.genres = genres;
        this.content = new BookContent(title, content);
    }

	public void addReview(Review review) {
        assert review.getBook().isKeyEqual(key);
		reviews.add(review);
	}

	public float getAverageRating() {
		return BigDecimal.valueOf(reviews.stream().mapToInt(Review::getRating).average().orElse(0))
				.setScale(1, RoundingMode.HALF_UP).floatValue();
	}

	public String getAuthorName() {
		return author != null ? author.getName() : null;
	}
}

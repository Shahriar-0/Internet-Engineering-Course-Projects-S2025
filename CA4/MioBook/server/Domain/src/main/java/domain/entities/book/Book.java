package domain.entities.book;

import domain.entities.DomainEntity;
import domain.entities.author.Author;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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

	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@Builder.Default
	private LocalDateTime dateAdded = LocalDateTime.now();

	@Builder.Default
	private String coverLink = null;

	public String getTitle() {
		return super.getKey();
	}

	public Book(
		String title,
		Author author,
		String publisher,
		int publishedYear,
		long basePrice,
		String synopsis,
		List<String> genres,
		String content
	) {
		super(title);
		this.author = author;
		this.publisher = publisher;
		this.publishedYear = publishedYear;
		this.basePrice = basePrice;
		this.synopsis = synopsis;
		this.genres = genres;
		this.content = new BookContent(title, content, author.getName());
	}

	public void addReview(Review review) {
		assert review.getBook().isKeyEqual(key);

		reviews.removeIf(r -> r.isKeyEqual(review.getKey())); // replace previous review of the same customer
		reviews.add(review);
	}

	public float getAverageRating() {
		return BigDecimal.valueOf(reviews.stream().mapToInt(Review::getRating).average().orElse(0)).setScale(1, RoundingMode.HALF_UP).floatValue();
	}
}

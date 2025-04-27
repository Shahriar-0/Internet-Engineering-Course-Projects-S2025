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
public class Book extends DomainEntity {

    private String title;
	private Author author;
	private String publisher;
	private Integer publishedYear;
	private Long basePrice; // in cents
	private String synopsis;
	private final List<String> genres;
	private final BookContent content;
    private String imageLink;

	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

	@Builder.Default
	private LocalDateTime dateAdded = LocalDateTime.now();

	@Builder.Default
	private String coverLink = null;

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
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishedYear = publishedYear;
		this.basePrice = basePrice;
		this.synopsis = synopsis;
		this.genres = genres;
		this.content = new BookContent(this, content);
	}

	public void addReview(Review review) {
		assert review.getBook().equals(this);

		reviews.removeIf(r -> r.equals(review));
		reviews.add(review);
	}

	public float getAverageRating() {
		return BigDecimal.valueOf(reviews.stream().mapToInt(Review::getRating).average().orElse(0))
            .setScale(1, RoundingMode.HALF_UP).floatValue();
	}

    public boolean isTitleEqual(String title) {
        if (this.title == null)
            return false;

        return this.title.equals(title);
    }
}

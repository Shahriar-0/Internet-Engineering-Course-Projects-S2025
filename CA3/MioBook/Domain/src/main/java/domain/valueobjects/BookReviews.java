package domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class BookReviews {

	private List<Review> reviews;

	public BookReviews() {
		this.reviews = new ArrayList<>();
	}

	public float getAverageRating() {
		return BigDecimal.valueOf(reviews.stream().mapToInt(Review::rating).average().orElse(0)).setScale(1, RoundingMode.HALF_UP).floatValue();
	}

	public void add(Review review) {
		reviews.add(review);
	}
}

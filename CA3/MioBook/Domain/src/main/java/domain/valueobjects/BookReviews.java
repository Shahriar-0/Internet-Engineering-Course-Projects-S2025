package domain.valueobjects;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// TODO: move this list to customer
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
		// if a review already exists for that user, replace it
        reviews.removeIf(r -> r.getUsername().equals(review.getUsername()));
        reviews.add(review);
    }
}

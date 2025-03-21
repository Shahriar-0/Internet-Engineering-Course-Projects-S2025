package domain.valueobjects;

import domain.entities.User;

import java.time.LocalDateTime;

public record Review(int rating, String comment, User customer, LocalDateTime date) {
	public Review(int rating, String comment, User customer) {
		this(rating, comment, customer, LocalDateTime.now());
	}

	public String getUsername() {
		return customer.getUsername();
	}
}

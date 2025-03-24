package domain.valueobjects;

import domain.entities.User;
import java.time.LocalDateTime;

// TODO: make this class and add update method, add minimum and maximum rating, add validation, User -> Customer, move to domain entity
public record Review(int rating, String comment, User customer, LocalDateTime date) {
	public Review(int rating, String comment, User customer) {
		this(rating, comment, customer, LocalDateTime.now());
	}

	public String getUsername() {
		return customer.getUsername();
	}
}

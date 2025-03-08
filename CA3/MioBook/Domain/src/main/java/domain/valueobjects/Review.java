package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.entities.User;
import java.time.LocalDateTime;

public record Review(
	int rating,
	String comment,

	@JsonIgnore
	User customer,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime date
) {
	public Review(int rating, String comment, User customer) {
		this(rating, comment, customer, LocalDateTime.now());
	}

	@JsonProperty("username")
	public String getUsername() {
		return customer.getUsername();
	}
}

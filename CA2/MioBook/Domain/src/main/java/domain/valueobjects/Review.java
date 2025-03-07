package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record Review(
	int rating,
	String comment,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime date
) {
	public Review(int rating, String comment) {
		this(rating, comment, LocalDateTime.now());
	}
}

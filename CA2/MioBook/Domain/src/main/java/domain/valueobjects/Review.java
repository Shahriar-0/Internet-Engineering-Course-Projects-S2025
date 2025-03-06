package domain.valueobjects;

import java.time.LocalDateTime;

public record Review(int rating, String comment, LocalDateTime date) {
    public Review(int rating, String comment) {
        this(rating, comment, LocalDateTime.now());
    }
}

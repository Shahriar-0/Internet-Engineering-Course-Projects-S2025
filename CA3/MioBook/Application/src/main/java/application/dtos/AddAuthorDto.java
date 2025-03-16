package application.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAuthorDto(
	@NotBlank(message = "Username is required")
	String username,

	@NotBlank(message = "Name is required")
	String name,

	@NotBlank(message = "Pen username is required")
	String penName,

	@NotBlank(message = "Nationality is required")
	String nationality,

	@NotBlank(message = "Born is required")
	@Pattern(
		regexp = "^\\d{4}-\\d{2}-\\d{2}$",
		message = "Invalid date format. Expected format: YYYY-MM-DD"
	)
	String born,

	@Pattern(
		regexp = "^\\d{4}-\\d{2}-\\d{2}$",
		message = "Invalid date format. Expected format: YYYY-MM-DD"
	)
	String died
) {
	@AssertTrue(message = "From and to years must be consistent")
    private boolean isYearRangeConsistent() {
		LocalDate bornDate = LocalDate.parse(this.born);
		LocalDate diedDate = this.died == null ? null : LocalDate.parse(this.died);
        return born == null || died == null || bornDate.isBefore(diedDate);
    }
}

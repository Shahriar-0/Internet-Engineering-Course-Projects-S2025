package application.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAuthorDto(
	@NotBlank(message = "Username is required")
	@JsonProperty(value = "username", required = true)
	String username,

	@NotBlank(message = "Name is required")
	@JsonProperty(value = "name", required = true)
	String name,

	@NotBlank(message = "Pen name is required")
	@JsonProperty(value = "penName", required = true)
	String penName,

	@NotBlank(message = "Nationality is required")
	@JsonProperty(value = "nationality", required = true)
	String nationality,

	@NotBlank(message = "Born is required")
	@JsonProperty(value = "born", required = true)
	@Pattern(
		regexp = "^\\d{4}-\\d{2}-\\d{2}$",
		message = "Invalid date format. Expected format: YYYY-MM-DD"
	)
	String born,

	@NotBlank(message = "Died is required")
	@JsonProperty(value = "died") 	// It can be null
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

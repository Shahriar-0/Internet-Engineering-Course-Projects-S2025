package application.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BorrowBookDto(
	@NotBlank(message = "Username is required")
	String username,

	@NotBlank(message = "Title is required")
	String title,

	@NotNull(message = "Borrowed date is required")
	@Min(value = 1, message = "Borrowed days must be greater or equal to 1")
	@Max(value = 9, message = "Borrowed days must be less than 10")
	Integer borrowedDays
) {}

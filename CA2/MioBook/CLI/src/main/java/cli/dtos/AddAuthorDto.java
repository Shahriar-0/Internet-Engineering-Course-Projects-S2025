package cli.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record AddAuthorDto(
	@JsonProperty(value = "username", required = true)
	String username,

	@JsonProperty(value = "name", required = true)
	String name,

	@JsonProperty(value = "penName", required = true)
	String penName,

	@JsonProperty(value = "nationality", required = true)
	String nationality,

	@JsonProperty(value = "born", required = true)
	@Pattern(
		regexp = "^\\d{4}-\\d{2}-\\d{2}$",
		message = "Invalid date format. Expected format: YYYY-MM-DD"
	)
	String born,

	@JsonProperty(value = "died") 	// It can be null
	@Pattern(
		regexp = "^\\d{4}-\\d{2}-\\d{2}$",
		message = "Invalid date format. Expected format: YYYY-MM-DD"
	)
	String died
) {}

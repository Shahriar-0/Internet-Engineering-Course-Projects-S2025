package cli.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.valueobjects.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddUserDto(
	@Pattern(regexp = "^(customer|admin)$", message = "Role must be either 'customer' or 'admin'")
	@NotBlank(message = "Role is required")
	@JsonProperty(value = "role", required = true)
	String role,

	@NotBlank(message = "Username is required")
	@JsonProperty(value = "username", required = true)
	@Pattern(
		regexp = "^[a-zA-Z0-9_-]+$",
		message = "Username must contain only letters, numbers, underscores, hyphens, or underscores"
	)
	String username,

	@NotBlank(message = "Password is required")
	@JsonProperty(value = "password", required = true)
	@Size(min = 4, message = "Password must be at least 4 characters long")
	String password,

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@JsonProperty(value = "email", required = true)
	String email,

	@NotNull(message = "Address is required") @Valid @JsonProperty(value = "address", required = true) Address address
) {}

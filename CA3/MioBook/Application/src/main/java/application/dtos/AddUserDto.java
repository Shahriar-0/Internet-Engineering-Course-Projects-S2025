package application.dtos;

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
	String role,

	@NotBlank(message = "Username is required")
	@Pattern(
		regexp = "^[a-zA-Z0-9_-]+$",
		message = "Username must contain only letters, numbers, underscores, hyphens, or underscores"
	)
	String username,

	@NotBlank(message = "Password is required")
	@Size(min = 4, message = "Password must be at least 4 characters long")
	String password,

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	String email,

	@NotNull(message = "Address is required")
    @Valid
    Address address
) {}

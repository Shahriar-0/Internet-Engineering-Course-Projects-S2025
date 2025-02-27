package cli.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.valueobjects.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddUserDto(
    @NotBlank(message = "Role is required") @JsonProperty(value = "role", required = true) String role,

    @NotBlank(message = "Username is required") @JsonProperty(value = "username", required = true) String username,

    @NotBlank(message = "Password is required") @JsonProperty(value = "password", required = true) String password,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty(value = "email", required = true) String email,

    @NotNull(message = "Address is required")
    @Valid
    @JsonProperty(value = "address", required = true) Address address
) {}
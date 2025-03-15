package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowUserDetailsDto(
    @NotBlank(message = "Username is required")
    String username
) {}

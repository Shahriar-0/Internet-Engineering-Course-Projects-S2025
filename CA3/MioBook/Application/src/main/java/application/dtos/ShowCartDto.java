package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowCartDto(
    @NotBlank(message = "Username is required")
    String username
) {}

package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowAuthorDetailsDto(
    @NotBlank(message = "Name is required")
    String name
) {}

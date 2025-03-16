package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowBookDetailsDto(
    @NotBlank(message = "Title is required")
    String title
) {}

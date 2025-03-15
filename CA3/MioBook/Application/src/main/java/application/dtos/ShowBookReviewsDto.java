package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowBookReviewsDto(
    @NotBlank(message = "Title is required")
    String title
) {}

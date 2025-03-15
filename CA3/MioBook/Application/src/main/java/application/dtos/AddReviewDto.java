package application.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddReviewDto(
    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Title is required")
    String title,

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating,

    @NotBlank(message = "Comment is required")
    String comment
) {}

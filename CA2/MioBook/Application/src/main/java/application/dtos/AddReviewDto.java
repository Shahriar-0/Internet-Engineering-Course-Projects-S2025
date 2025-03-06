package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddReviewDto(
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username,

    @NotBlank(message = "Title is required")
    @JsonProperty(value = "title", required = true)
    String title,

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @JsonProperty(value = "rate", required = true)
    Integer rating,

    @NotBlank(message = "Comment is required")
    @JsonProperty(value = "comment", required = true)
    String comment
) {}

package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ShowBookReviewsDto(
    @NotBlank(message = "Title is required")
    @JsonProperty(value = "title", required = true)
    String title
) {}

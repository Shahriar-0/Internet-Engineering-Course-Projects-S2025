package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record BookContent(
    @NotBlank(message = "Content is required")
    @JsonProperty(value = "content", required = true)
    String content
) {}

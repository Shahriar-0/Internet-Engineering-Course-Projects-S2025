package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ShowAuthorDetailsDto(
    @NotBlank(message = "Name is required")
    @JsonProperty(value = "username", required = true)
    String name
) {}

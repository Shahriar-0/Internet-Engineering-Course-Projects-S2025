package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RemoveCartDto (
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username,

    @NotBlank(message = "Title is required")
    @JsonProperty(value = "title", required = true)
    String title
) {}

package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ShowUserDetailsDto(
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username
) {}

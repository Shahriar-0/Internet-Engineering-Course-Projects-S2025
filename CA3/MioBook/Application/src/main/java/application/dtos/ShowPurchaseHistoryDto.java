package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ShowPurchaseHistoryDto(
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username
) {}

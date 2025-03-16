package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowPurchaseHistoryDto(
    @NotBlank(message = "Username is required")
    String username
) {}

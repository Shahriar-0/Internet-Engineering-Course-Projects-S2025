package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ShowPurchasedBooksDto(
    @NotBlank(message = "Username is required")
    String username
) {}

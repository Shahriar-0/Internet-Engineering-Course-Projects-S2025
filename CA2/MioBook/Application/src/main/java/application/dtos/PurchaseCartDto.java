package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record PurchaseCartDto(
    @NotBlank(message = "Username is required")
    String username
) {}

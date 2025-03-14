package domain.valueobjects;

import jakarta.validation.constraints.NotBlank;

public record Address(
    @NotBlank(message = "Country is required")
    String country,

    @NotBlank(message = "City is required")
    String city
) {}

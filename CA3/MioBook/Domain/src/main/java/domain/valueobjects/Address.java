package domain.valueobjects;

import jakarta.validation.constraints.NotBlank;

public record Address(
    @NotBlank String country,
    @NotBlank String city
) {}

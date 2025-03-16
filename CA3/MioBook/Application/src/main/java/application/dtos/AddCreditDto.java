package application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddCreditDto(
    @NotBlank(message = "Username is required")
    String username,

    @Min(value = 100, message = "Credit amount must be greater or equal to 100 cent")
    long credit
) {}

package application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddCreditDto(
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username,

    @Min(value = 100, message = "Credit amount must be greater than 100 cent")
    @JsonProperty(value = "credit", required = true)
    long credit
) {}

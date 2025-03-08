package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record Address(
    @NotBlank(message = "Country is required")
    @JsonProperty(value = "country", required = true)
    String country,

    @NotBlank(message = "City is required")
    @JsonProperty(value = "city", required = true)
    String city
) {}

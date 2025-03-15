package application.dtos;

import jakarta.validation.constraints.NotBlank;

public record RemoveCartDto (
    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Title is required")
    String title
) {}

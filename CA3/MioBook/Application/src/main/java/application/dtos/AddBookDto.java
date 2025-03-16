package application.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddBookDto(
    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Author is required")
    String author,

    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Publisher is required")
    String publisher,

    @Positive(message = "Year must be a positive number")
    int year,

    @Positive(message = "Price must be a positive number")
    long price,

    @NotBlank(message = "Synopsis is required")
    String synopsis,

    @NotBlank(message = "Content is required")
    String content,

    @NotNull(message = "Genres list cannot be null")
    @NotEmpty(message = "Genres list cannot be empty")
    @Valid
    List<@NotBlank(message = "Genre cannot be blank") String> genres
) {}

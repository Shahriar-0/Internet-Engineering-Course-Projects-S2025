package cli.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddBookDto(
    @NotBlank(message = "Username is required")
    @JsonProperty(value = "username", required = true)
    String username,

    @NotBlank(message = "Author is required")
    @JsonProperty(value = "author", required = true)
    String author,

    @NotBlank(message = "Title is required")
    @JsonProperty(value = "title", required = true)
    String title,

    @NotBlank(message = "Publisher is required")
    @JsonProperty(value = "publisher", required = true)
    String publisher,

    @Positive(message = "Year must be a positive number")
    @JsonProperty(value = "year", required = true)
    int year,

    @Positive(message = "Price must be a positive number")
    @JsonProperty(value = "price", required = true)
    long price,

    @NotBlank(message = "Synopsis is required")
    @JsonProperty(value = "synopsis", required = true)
    String synopsis,

    @NotBlank(message = "Content is required")
    @JsonProperty(value = "content", required = true)
    String content,

    @NotNull(message = "Genres list cannot be null")
    @NotEmpty(message = "Genres list cannot be empty")
    @Valid
    @JsonProperty(value = "genres", required = true)
    List<@NotBlank(message = "Genre cannot be blank") String> genres
) {}

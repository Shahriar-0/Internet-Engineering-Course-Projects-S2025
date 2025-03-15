package application.dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;

public record SearchBooksDto(
    @JsonProperty("title")
    String title,

    @JsonProperty("username")
    String authorName,

    @JsonProperty("genre")
    String genre,

    @JsonProperty("from")
    Integer yearFrom,

    @JsonProperty("to")
    Integer yearTo
) {
    @AssertTrue(message = "At least one search field must be provided")
    private boolean isAtLeastOneFieldProvided() {
        return title != null || authorName != null || genre != null || yearFrom != null || yearTo != null;
    }

    @AssertTrue(message = "Both 'from' and 'to' must be provided together")
    private boolean isFromAndToConsistent() {
        if (yearFrom == null && yearTo == null) {
            return true;
        }
        return yearFrom != null && yearTo != null;
    }

    @AssertTrue(message = "From and to years must be consistent")
    private boolean isYearRangeConsistent() {
        return yearFrom == null || yearTo == null || yearFrom <= yearTo;
    }

    public boolean isCompatibleWithSearchType(String by) {
        if (by.equals("title"))
            return title != null;
        if (by.equals("author"))
            return authorName != null;
        if (by.equals("genre"))
            return genre != null;
        if (by.equals("year"))
            return yearFrom != null && yearTo != null;
        return false;
    }

    private boolean onlyOneFieldProvided() {
        long fieldCount = Stream.of(
                title != null,
                authorName != null,
                genre != null,
                (yearFrom != null && yearTo != null)
            ).filter(field -> field).count();

        return fieldCount == 1;
    }

    private String getOneFieldProvidedMessage() {
        if (title != null)
            return "Books containing '" + title + "' in their title:";
        if (authorName != null)
            return "Books by '" + authorName + "':";
        if (genre != null)
            return "Books in the '" + genre + "' genre:";
        if (yearFrom != null && yearTo != null)
            return "Books published between " + yearFrom + " and " + yearTo + ":";
        return null;
    }

    public String getSuccessMessage() {
        if (onlyOneFieldProvided())
            return getOneFieldProvidedMessage();
        return "Search results with the following parameters (" + getParams() + "):";
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        if (title != null)
            params.put("title", title);
        if (authorName != null)
            params.put("username", authorName);
        if (genre != null)
            params.put("genre", genre);
        if (yearFrom != null)
            params.put("from", yearFrom.toString());
        if (yearTo != null)
            params.put("to", yearTo.toString());
        return params;
    }
}
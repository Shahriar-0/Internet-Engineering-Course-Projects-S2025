package cli.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record Response (
    boolean success,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object data
) {}

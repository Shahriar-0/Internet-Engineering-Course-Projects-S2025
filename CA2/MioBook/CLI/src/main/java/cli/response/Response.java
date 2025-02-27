package cli.response;

import application.result.Result;
import com.fasterxml.jackson.annotation.JsonInclude;

public record Response(
    Boolean success,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL) Object data
) {
    
	public <T> Response(Result<T> result, String message, Boolean hasData) {
		this(
			result.isSuccessful(),
			result.isSuccessful() ? message : result.getException().getMessage(),
			hasData ? result.getData() : null
		);
	}
}

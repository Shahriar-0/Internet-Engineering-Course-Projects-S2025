package cli.outputprocessors;

import cli.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CliWriter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public CliWriter() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	public void writeResponseToConsole(Response response) throws JsonProcessingException {
		String jsonString = objectMapper.writeValueAsString(response);
		System.out.println(jsonString);
	}
}

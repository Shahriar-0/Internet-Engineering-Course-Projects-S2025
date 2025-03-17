package webapi.configuration;

import application.uscase.UseCaseType;
import application.uscase.user.AddUserUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import webapi.services.UseCaseService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private static final String API_URL = "http://194.60.230.196:8000";
	private static final String USERS_API_URL = API_URL + "/users";
	private final UseCaseService useCaseService;

	@Override
	public void run(ApplicationArguments args) {
		loadUsers();
	}

	private void loadUsers() {
		AddUserUseCase addUser = (AddUserUseCase) useCaseService.getUseCase(UseCaseType.ADD_USER);
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(USERS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			List<AddUserUseCase.AddUserData> parsedUserData = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

			for (AddUserUseCase.AddUserData userData : parsedUserData) {
				addUser.perform(userData);
			}
		}
		catch (Exception e) {
			System.err.println("Error loading users: " + e.getMessage());
		}
	}
}

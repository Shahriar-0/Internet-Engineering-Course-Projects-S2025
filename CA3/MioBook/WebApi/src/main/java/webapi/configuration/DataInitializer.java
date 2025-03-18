package webapi.configuration;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.AddAuthorUseCase;
import application.usecase.admin.AddBookUseCase;
import application.usecase.customer.AddReviewUseCase;
import application.usecase.user.AddUserUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entities.Book;
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
	private static final String AUTHORS_API_URL = API_URL + "/authors";
	private static final String BOOKS_API_URL = API_URL + "/books";
	private static final String REVIEWS_API_URL = API_URL + "/reviews";
	private final UseCaseService useCaseService;

	@Override
	public void run(ApplicationArguments args) {
		loadUsers();
		loadAuthors();
		loadBooks();
		loadReviews();
	}

	private void loadUsers() {
		AddUserUseCase addUser = (AddUserUseCase) useCaseService.getUseCase(UseCaseType.ADD_USER);
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(USERS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			List<AddUserUseCase.AddUserData> parsedUserData = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

			parsedUserData.forEach(addUser::perform);
			parsedUserData.forEach(user -> System.out.println("User added: " + user.username()));
		}
		catch (Exception e) {
			System.err.println("Error loading users: " + e.getMessage());
		}
	}

	private void loadAuthors() {
		AddAuthorUseCase addAuthor = (AddAuthorUseCase) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(AUTHORS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			List<AddAuthorUseCase.AddAuthorData> parsedAuthorData = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

			parsedAuthorData.forEach(addAuthor::perform);
		}
		catch (Exception e) {
			System.err.println("Error loading authors: " + e.getMessage());
		}
	}

	private void loadBooks() {
		AddBookUseCase addBook = (AddBookUseCase) useCaseService.getUseCase(UseCaseType.ADD_BOOK);
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(BOOKS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			List<AddBookUseCase.AddBookData> parsedBookData = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

			parsedBookData.forEach(addBook::perform);
		}
		catch (Exception e) {
			System.err.println("Error loading books: " + e.getMessage());
		}
	}

	private void loadReviews() {
		AddReviewUseCase addReview = (AddReviewUseCase) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(REVIEWS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(jsonResponse);

			for (JsonNode node : rootNode) {
				String username = node.get("username").asText();
				String title = node.get("title").asText();
				int rating = node.get("rate").asInt();
				String comment = node.get("comment").asText();

				AddReviewUseCase.AddReviewData addReviewData = new AddReviewUseCase.AddReviewData(title, rating, comment);

				Result<Book> result = addReview.perform(addReviewData, username);
				if (result.isFailure()) {
					System.err.println("Failed to add review for book: " + title);
				}
			}
		}
		catch (Exception e) {
			System.err.println("Error loading reviews: " + e.getMessage());
		}
	}
}

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
import domain.entities.Author;
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

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				// String username = node.get("username").asText(); // not used since we are sure of data integrity
				String name = node.get("name").asText();
				String penName = node.get("penName").asText();
				String nationality = node.get("nationality").asText();
				String born = node.get("born").asText();
				String died = node.get("died") == null ? null : node.get("died").asText();

				AddAuthorUseCase.AddAuthorData data = new AddAuthorUseCase.AddAuthorData(name, penName, nationality, born, died);
				Result<Author> result = addAuthor.perform(data);
				if (result.isFailure())
					System.err.println("Failed to add author: " + result.getException().getMessage() + " because of: " + result.getException().getMessage());
			}
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

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				// String username = node.get("username").asText();
				String authorName = node.get("author").asText();
				String title = node.get("title").asText();
				String publisher = node.get("publisher").asText();
				Integer year = node.get("year").asInt();
				Long price = node.get("price").asLong();
				String synopsis = node.get("synopsis").asText();
				String content = node.get("content").asText();
				List<String> genres = objectMapper.convertValue(node.get("genres"), new TypeReference<List<String>>() {});

				AddBookUseCase.AddBookData data = new AddBookUseCase.AddBookData(authorName, title, publisher, year, price, synopsis, content, genres);
				Result<Book> result = addBook.perform(data);
				if (result.isFailure())
					System.err.println("Failed to add book: " + title + " because: " + result.getException().getMessage());
			}
		}
		catch (Exception e) {
			System.err.println("Error loading books: " + e.getMessage());
		}
	}

	private void loadReviews() {
		AddReviewUseCase addReview = (AddReviewUseCase) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);
		try {
			addReview.setEnforceAccessChecks(false); // Disable access checks during initialization
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(REVIEWS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				String username = node.get("username").asText();
				String title = node.get("title").asText();
				int rating = node.get("rate").asInt();
				String comment = node.get("comment").asText();

				AddReviewUseCase.AddReviewData addReviewData = new AddReviewUseCase.AddReviewData(title, rating, comment);

				Result<Book> result = addReview.perform(addReviewData, username);
				if (result.isFailure()) {
					System.err.println("Failed to add review for book: " + title + " because: " + result.getException().getMessage());
				}
			}
		}
		catch (Exception e) {
			System.err.println("Error loading reviews: " + e.getMessage());
		}
		finally {
            addReview.setEnforceAccessChecks(true); // Re-enable access checks after initialization
        }
	}
}

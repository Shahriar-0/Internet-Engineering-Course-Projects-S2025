package webapi.configuration;

import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.usecase.admin.author.AddAuthor;
import application.usecase.admin.book.AddBook;
import application.usecase.customer.book.AddReview;
import application.usecase.user.account.CreateAccount;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entities.author.Author;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import domain.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

	private static final String API_URL = "http://194.60.231.242:8000";
	private static final String USERS_API_URL = API_URL + "/users";
	private static final String AUTHORS_API_URL = API_URL + "/authors";
	private static final String BOOKS_API_URL = API_URL + "/books";
	private static final String REVIEWS_API_URL = API_URL + "/reviews";

	private final IUserRepository userRepository;
	private final IAuthorRepository authorRepository;
	private final IBookRepository bookRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	@Override
	public void run(ApplicationArguments args) {
		loadUsers();
		loadAuthors();
		loadBooks();
		loadReviews();
	}

	private void loadUsers() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(USERS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			List<CreateAccount.AddUserData> parsedUserData = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

			for (CreateAccount.AddUserData userData : parsedUserData) {
                User user = CreateAccount.mapToUser(userData);
				userRepository.save(user);
            }
		}
		catch (Exception e) {
            logger.error("Error loading users: " + e.getMessage());
		}
	}

	private void loadAuthors() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(AUTHORS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				String username = node.get("username").asText();
				Optional<User> userResult = userRepository.findByUsername(username);
				if (userResult.isEmpty())
                    throw new Exception("User with username " + username + " doesn't exist.");
				if (!Role.ADMIN.equals(userResult.get().getRole()))
					throw new Exception("User with username " + username + " is not an admin.");
				String name = node.get("name").asText();
				String penName = node.get("penName").asText();
				String nationality = node.get("nationality").asText();
				String born = node.get("born").asText();
				String died = node.get("died") == null ? null : node.get("died").asText();

				AddAuthor.AddAuthorData data = new AddAuthor.AddAuthorData(name, penName, nationality, born, died);
				Author author = AddAuthor.mapToAuthor(data, (Admin) userResult.get());
				authorRepository.save(author);
			}
		}
		catch (Exception e) {
			logger.error("Error loading authors: " + e.getMessage());
		}
	}

	private void loadBooks() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(BOOKS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				String username = node.get("username").asText();
				Optional<User> userResult = userRepository.findByUsername(username);
				if (userResult.isEmpty())
                    throw new Exception("User with username " + username + " doesn't exist.");
				if (!Role.ADMIN.equals(userResult.get().getRole()))
					throw new Exception("User with username " + username + " is not an admin.");

				String authorName = node.get("author").asText();
				Optional<Author> authorResult = authorRepository.findByName(authorName);
				if (authorResult.isEmpty())
					throw new Exception("Author with name " + authorName + " doesn't exist.");

				String title = node.get("title").asText();
				String publisher = node.get("publisher").asText();
				Integer year = node.get("year").asInt();
				Long price = node.get("price").asLong();
				String synopsis = node.get("synopsis").asText();
				String content = node.get("content").asText();
				List<String> genres = objectMapper.convertValue(node.get("genres"), new TypeReference<List<String>>() {});

				AddBook.AddBookData data = new AddBook.AddBookData(authorName, title, publisher, synopsis, content, year, price, genres, "", "");
				Book book = AddBook.mapToBook(data, authorResult.get(), (Admin) userResult.get());
				bookRepository.save(book);
			}
		}
		catch (Exception e) {
			logger.error("Error loading books: " + e.getMessage());
		}
	}

	private void loadReviews() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(REVIEWS_API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			for (JsonNode node : objectMapper.readTree(jsonResponse)) {
				String username = node.get("username").asText();
				Optional<User> userResult = userRepository.findByUsername(username);
				if (userResult.isEmpty())
                    throw new Exception("User with username " + username + " doesn't exist.");
				if (!Role.CUSTOMER.equals(userResult.get().getRole()))
					throw new Exception("User with username " + username + " is not a customer.");

				String title = node.get("title").asText();
				Optional<Book> bookResult = bookRepository.findByTitle(title);
				if (bookResult.isEmpty())
					throw new Exception("Book with title " + title + " doesn't exist.");

				int rating = node.get("rate").asInt();
				String comment = node.get("comment").asText();

				AddReview.AddReviewData addReviewData = new AddReview.AddReviewData(rating, comment);
				Review review = AddReview.mapToReview(addReviewData, bookResult.get(), (Customer) userResult.get());
				bookResult.get().addReview(review);
				bookRepository.upsertReview(review, bookResult.get(), (Customer) userResult.get());
			}
		}
		catch (Exception e) {
			logger.error("Error loading reviews: " + e.getMessage());
		}
	}
}

package cli.inputprocessors;

import application.dtos.*;
import application.services.*;
import cli.command.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandGenerator {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static final Validator validator = factory.getValidator();

	private final UserService userService;
	private final AdminService adminService;

	/**
	 * Given a string command in the format "COMMAND_NAME JSON_DATA",
	 * parse the JSON data and create a corresponding command object.
	 *
	 * @param input A string command in the format "COMMAND_NAME JSON_DATA".
	 * @return A command object corresponding to the given command name and data.
	 * @throws JsonProcessingException If the JSON data cannot be parsed.
	 * @throws IllegalArgumentException If the command name is invalid.
	 */
	public IBaseCommand generateCommand(String input) throws JsonProcessingException, IllegalArgumentException {
		String jsonString = input.substring(input.indexOf(" ") + 1);
		CommandType type = CommandType.valueOf(input.split(" ")[0].toUpperCase());
		return switch (type) {
			case ADD_USER -> {
				AddUserDto dto = objectMapper.readValue(jsonString, AddUserDto.class);
				validate(dto);
				yield new AddUserCommand(dto, userService);
			}
			case ADD_AUTHOR -> {
				AddAuthorDto dto = objectMapper.readValue(jsonString, AddAuthorDto.class);
				validate(dto);
				yield new AddAuthorCommand(dto, adminService);
			}
			case ADD_BOOK -> {
				AddBookDto dto = objectMapper.readValue(jsonString, AddBookDto.class);
				validate(dto);
				yield new AddBookCommand(dto, adminService);
			}
			case ADD_CART -> {
				AddCartDto dto = objectMapper.readValue(jsonString, AddCartDto.class);
				validate(dto);
				yield new AddCartCommand(dto, userService);
			}
			case REMOVE_CART -> {
				RemoveCartDto dto = objectMapper.readValue(jsonString, RemoveCartDto.class);
				validate(dto);
				yield new RemoveCartCommand(dto, userService);
			}
			case ADD_CREDIT -> {
				AddCreditDto dto = objectMapper.readValue(jsonString, AddCreditDto.class);
				validate(dto);
				yield new AddCreditCommand(dto, userService);
			}
			case PURCHASE_CART -> {
				PurchaseCartDto dto = objectMapper.readValue(jsonString, PurchaseCartDto.class);
				validate(dto);
				yield new PurchaseCartCommand(dto, userService);
			}
			case BORROW_BOOK -> {
				BorrowBookDto dto = objectMapper.readValue(jsonString, BorrowBookDto.class);
				validate(dto);
				yield new BorrowBookCommand(dto, userService);
			}
			case ADD_REVIEW -> {
				AddReviewDto dto = objectMapper.readValue(jsonString, AddReviewDto.class);
				validate(dto);
				yield new AddReviewCommand(dto, userService);
			}
			case SHOW_USER_DETAILS -> {
				ShowUserDetailsDto dto = objectMapper.readValue(jsonString, ShowUserDetailsDto.class);
				validate(dto);
				yield new ShowUserDetailsCommand(dto, userService);
			}
			case SHOW_AUTHOR_DETAILS -> {
				ShowAuthorDetailsDto dto = objectMapper.readValue(jsonString, ShowAuthorDetailsDto.class);
				validate(dto);
				yield new ShowAuthorDetailsCommand(dto, userService);
			}
			case SHOW_BOOK_DETAILS -> {
				ShowBookDetailsDto dto = objectMapper.readValue(jsonString, ShowBookDetailsDto.class);
				validate(dto);
				yield new ShowBookDetailsCommand(dto, userService);
			}
			case SHOW_BOOK_REVIEWS -> {
				ShowBookReviewsDto dto = objectMapper.readValue(jsonString, ShowBookReviewsDto.class);
				validate(dto);
				yield new ShowBookReviewsCommand(dto, userService);
			}
			case SHOW_CART -> {
				ShowCartDto dto = objectMapper.readValue(jsonString, ShowCartDto.class);
				validate(dto);
				yield new ShowCartCommand(dto, userService);
			}
			case SHOW_PURCHASE_HISTORY -> {
				ShowPurchaseHistoryDto dto = objectMapper.readValue(jsonString, ShowPurchaseHistoryDto.class);
				validate(dto);
				yield new ShowPurchaseHistoryCommand(dto, userService);
			}
			case SHOW_PURCHASED_BOOKS -> {
				ShowPurchasedBooksDto dto = objectMapper.readValue(jsonString, ShowPurchasedBooksDto.class);
				validate(dto);
				yield new ShowPurchasedBooksCommand(dto, userService);
			}
			case SEARCH_BOOKS_BY_TITLE, SEARCH_BOOKS_BY_AUTHOR, SEARCH_BOOKS_BY_GENRE, SEARCH_BOOKS_BY_YEAR -> {
				SearchBooksDto dto = objectMapper.readValue(jsonString, SearchBooksDto.class);
				validate(dto);
				validateSearchBooksDto(dto, type);
				yield new SearchBooksCommand(dto, userService);
			}
		};
	}

	/**
	 * Validate the given object against the constraints specified in its class.
	 *
	 * @param object The object to validate.
	 * @throws IllegalArgumentException If the object is invalid.
	 */
	private static <T> void validate(T object) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Validation failed: ");
			for (ConstraintViolation<T> violation : violations) {
				errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append(",");
			}
			throw new IllegalArgumentException(errorMessage.toString());
		}
	}

	/**
	 * Validate the given SearchBooksDto against the constraints specified
	 * in the given CommandType. Specifically, ensure that the given
	 * SearchBooksDto is compatible with the given CommandType.
	 *
	 * @param dto The SearchBooksDto to validate.
	 * @param type The CommandType to validate against.
	 */
	private static void validateSearchBooksDto(SearchBooksDto dto, CommandType type) { // FIXME: sorry for this
		String by = type.toString().substring(type.toString().indexOf("BY") + "BY_".length()).toLowerCase();
		if (!dto.isCompatibleWithSearchType(by)) throw new IllegalArgumentException("Search for '" + by + "' requires a '" + by + "' parameter");
	}
}

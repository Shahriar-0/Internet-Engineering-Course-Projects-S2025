package application.usecase.admin.author;

import application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists;
import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Author;
import domain.entities.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AddAuthor implements IUseCase {

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_AUTHOR;
	}

	public Result<Author> perform(AddAuthorData data, User user) {
		assert User.Role.ADMIN.equals(user.getRole()): "we relay on presentation layer access control";

		if (authorRepository.exists(data.name))
			return Result.failure(new AuthorAlreadyExists(data.name));

		return authorRepository.add(mapToAuthor(data));
	}

	private static Author mapToAuthor(AddAuthorData data) {
		return Author
			.builder()
			.key(data.name)
			.penName(data.penName)
			.nationality(data.nationality)
			.born(LocalDate.parse(data.born))
			.died(data.died == null ? null : LocalDate.parse(data.died))
			.build();
	}

	public record AddAuthorData(
		@NotBlank String name,
		@NotBlank String penName,
		@NotBlank String nationality,

		@NotBlank
		@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Expected format: YYYY-MM-DD")
		String born,

		@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Expected format: YYYY-MM-DD")
		String died
	) {
		@AssertTrue(message = "From and to years must be consistent")
		private boolean isYearRangeConsistent() {
			try {
				LocalDate bornDate = LocalDate.parse(this.born);
				LocalDate diedDate = this.died == null ? null : LocalDate.parse(this.died);
				return died == null || bornDate.isBefore(diedDate);
			}
			catch (Exception e) {
				return false;
			}
		}
	}
}

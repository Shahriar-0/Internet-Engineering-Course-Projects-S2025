package application.usecase.admin.author;

import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.author.Author;
import domain.entities.user.Role;
import domain.entities.user.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddAuthor implements IUseCase {

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_AUTHOR;
	}

	public Result<Author> perform(AddAuthorData data, User user) {
		assert Role.ADMIN.equals(user.getRole()): "we rely on presentation layer access control";

		return authorRepository.save(mapToAuthor(data));
	}

	private static Author mapToAuthor(AddAuthorData data) {
		if (data.died == null)
            return Author.createAliveAuthor(
                data.name,
                data.penName,
                data.nationality,
                LocalDate.parse(data.born)
            );
        else
            return Author.createDeadAuthor(
                data.name,
                data.penName,
                data.nationality,
                LocalDate.parse(data.born),
                LocalDate.parse(data.died)
            );
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

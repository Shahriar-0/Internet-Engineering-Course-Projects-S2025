package webapi.views.author;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import domain.entities.author.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorView {
	String name;
	String penName;
	String nationality;
	LocalDate born;
	LocalDate died;
	int bookCount;

	public AuthorView(Author entity) {
		this(
			entity.getName(),
			entity.getPenName(),
			entity.getNationality(),
			entity.getBorn(),
			entity.getDied(),
			entity.getBookCount()
		);
	}

	public static List<AuthorView> mapToView(List<Author> authors) {
		return authors.stream().map(AuthorView::new).toList();
	}

	public static Page<AuthorView> mapToView(Page<Author> authorPage) {
		return authorPage.map(AuthorView::new);
	}
}

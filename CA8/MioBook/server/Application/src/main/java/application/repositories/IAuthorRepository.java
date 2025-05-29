package application.repositories;

import application.usecase.user.author.GetAuthor;
import domain.entities.author.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IAuthorRepository extends IBaseRepository<Author> {
	Optional<Author> findByName(String name);
	List<Author> findAllWithBooks();
	Page<Author> filter(GetAuthor.AuthorFilter filter);
}

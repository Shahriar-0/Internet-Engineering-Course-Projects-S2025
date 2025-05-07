package application.repositories;

import domain.entities.author.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorRepository extends IBaseRepository<Author> {
    Optional<Author> findByName(String name);
    List<Author> findAllWithBooks();
}

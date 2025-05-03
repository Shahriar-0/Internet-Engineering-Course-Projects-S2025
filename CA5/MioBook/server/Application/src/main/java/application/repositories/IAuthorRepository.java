package application.repositories;

import domain.entities.author.Author;

import java.util.Optional;

public interface IAuthorRepository extends IBaseRepository<Author> {
    Optional<Author> findByName(String name);
}

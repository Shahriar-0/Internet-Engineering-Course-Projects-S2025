package application.repositories;

import application.result.Result;
import domain.entities.author.Author;

import java.util.Optional;

public interface IAuthorRepository extends IBaseRepository<Author> {
    Result<Author> findByName(String name);
}

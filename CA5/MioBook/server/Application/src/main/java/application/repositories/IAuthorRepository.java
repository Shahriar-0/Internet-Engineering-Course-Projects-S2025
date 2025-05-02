package application.repositories;

import application.result.Result;
import domain.entities.author.Author;

public interface IAuthorRepository extends IBaseRepository<Author> {
    Result<Author> findByName(String name);
}

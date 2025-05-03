package infra.repositories;

import application.exceptions.dataaccessexceptions.EntityDoesNotExist;
import application.repositories.IAuthorRepository;
import application.result.Result;
import domain.entities.author.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepository extends BaseRepository<Author> implements IAuthorRepository {

	@Override
	protected Class<Author> getEntityClassType() {
		return Author.class;
	}

	@Override
	protected Author copyOf(Author persistedEntity) {
		return Author
			.builder()
			.name(persistedEntity.getName())
			.penName(persistedEntity.getPenName())
			.nationality(persistedEntity.getNationality())
			.born(persistedEntity.getBorn())
			.died(persistedEntity.getDied())
			.books(persistedEntity.getBooks())
			.build();
	}

    @Override
    public Result<Author> findByName(String name) {
        for (Author author : map.values()) {
            if (name.equals(author.getName()))
                return Result.success(author);
        }

        return Result.failure(new EntityDoesNotExist(getEntityClassType(), name));
    }
}

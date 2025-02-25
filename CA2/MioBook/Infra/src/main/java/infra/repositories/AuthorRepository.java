package infra.repositories;

import application.repositories.IAuthorRepository;
import domain.entities.Author;

public class AuthorRepository extends BaseRepository<String, Author> implements IAuthorRepository {

	@Override
	protected Class<Author> getEntityClassType() {
		return Author.class;
	}

	@Override
	protected Author copyOf(Author persistedEntity) {
		return Author
			.builder()
			.key(persistedEntity.getName())
			.penName(persistedEntity.getPenName())
			.nationality(persistedEntity.getNationality())
			.born(persistedEntity.getBorn())
			.died(persistedEntity.getDied())
			.build();
	}
}

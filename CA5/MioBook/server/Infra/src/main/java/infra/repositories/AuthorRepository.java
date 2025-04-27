package infra.repositories;

import application.repositories.IAuthorRepository;
import domain.entities.author.Author;

import java.util.Optional;

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
    public Optional<Author> findByName(String name) {
        for (Author author : map.values()) {
            if (name.equals(author.getName()))
                return Optional.of(author);
        }

        return Optional.empty();
    }
}

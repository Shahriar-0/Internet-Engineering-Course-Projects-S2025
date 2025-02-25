package infra.repositories;

import application.repositories.IUserRepository;
import domain.entities.User;

public class UserRepository extends BaseRepository<String, User> implements IUserRepository {

	@Override
	protected Class<User> getEntityClassType() {
		return User.class;
	}

	@Override
	protected User copyOf(User persistedEntity) {
		return User
			.builder()
			.key(persistedEntity.getUsername())
			.address(persistedEntity.getAddress())
			.password(persistedEntity.getPassword())
			.email(persistedEntity.getEmail())
			.role(persistedEntity.getRole())
			.credit(persistedEntity.getCredit())
			.build();
	}

	@Override
	public boolean doesEmailExist(String email) {
		return map.values().stream().anyMatch(user -> user.getEmail().equals(email));
	}
}

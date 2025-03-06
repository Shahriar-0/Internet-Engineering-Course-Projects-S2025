package infra.repositories;

import application.repositories.IUserRepository;
import domain.entities.*;

public class UserRepository extends BaseRepository<String, User> implements IUserRepository {

	@Override
	protected Class<User> getEntityClassType() {
		return User.class;
	}

	@Override
	protected User copyOf(User persistedEntity) {
		if (persistedEntity instanceof Customer)
			return Customer
				.builder()
				.key(persistedEntity.getUsername())
				.address(persistedEntity.getAddress())
				.password(persistedEntity.getPassword())
				.email(persistedEntity.getEmail())
				.role(persistedEntity.getRole())
				.credit(((Customer) persistedEntity).getCredit())
				.build();
		else if (persistedEntity instanceof Admin)
			return Admin
				.builder()
				.key(persistedEntity.getUsername())
				.address(persistedEntity.getAddress())
				.password(persistedEntity.getPassword())
				.email(persistedEntity.getEmail())
				.role(persistedEntity.getRole())
				.build();
		else
			throw new IllegalArgumentException("Invalid user type: " + persistedEntity);
	}

	@Override
	public Boolean doesEmailExist(String email) {
		return map.values().stream().anyMatch(user -> user.getEmail().equals(email));
	}
}

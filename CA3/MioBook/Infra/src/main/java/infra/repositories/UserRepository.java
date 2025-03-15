package infra.repositories;

import application.repositories.IUserRepository;
import domain.entities.*;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<User> findByEmail(String email) {
		List<User> userList = map.values().stream().filter(u -> u.getEmail().equals(email)).toList();
		if (userList.isEmpty())
			return Optional.empty();

		return Optional.of(userList.get(0));
	}
}

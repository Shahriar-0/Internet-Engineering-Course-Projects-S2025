package application.repositories;

import domain.entities.user.User;

import java.util.Optional;

public interface IUserRepository extends IBaseRepository<String, User> {
	Optional<User> findByEmail(String email);
}

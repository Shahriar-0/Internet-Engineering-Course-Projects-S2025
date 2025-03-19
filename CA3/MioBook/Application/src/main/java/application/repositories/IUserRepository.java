package application.repositories;

import domain.entities.User;
import java.util.Optional;

public interface IUserRepository extends IBaseRepository<String, User> {
	Boolean doesEmailExist(String email);
	Optional<User> findByEmail(String email);
}

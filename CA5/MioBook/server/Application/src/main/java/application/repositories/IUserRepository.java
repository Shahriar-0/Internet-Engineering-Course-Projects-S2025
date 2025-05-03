package application.repositories;

import domain.entities.user.User;

import java.util.Optional;

public interface IUserRepository extends IBaseRepository<User> {
	Boolean doesEmailExist(String email);
	Optional<User> findByEmail(String email);
    Boolean doesUsernameExist(String username);
    Optional<User> findByUsername(String username);
}

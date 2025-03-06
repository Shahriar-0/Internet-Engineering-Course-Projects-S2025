package application.repositories;

import domain.entities.User;

public interface IUserRepository extends IBaseRepository<String, User> {
	Boolean doesEmailExist(String email);
}

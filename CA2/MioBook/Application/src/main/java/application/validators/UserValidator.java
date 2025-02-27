package application.validators;

import application.exceptions.businessexceptions.userexceptions.*;
import application.repositories.IUserRepository;
import application.result.Result;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator implements IBaseValidator<User> {

	private final IUserRepository userRepo;

	@Override
	public Result<User> validate(User user) {
		if (userRepo.exists(user.getUsername()))
			return Result.failureOf(new UsernameAlreadyExists());

		if (userRepo.doesEmailExist(user.getEmail()))
			return Result.failureOf(new EmailAlreadyExists());

		return Result.successOf(user);
	}
}

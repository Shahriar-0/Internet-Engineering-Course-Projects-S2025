package application.services;

import static domain.entities.User.Role.Admin;

import application.repositories.IUserRepository;
import application.result.Result;
import application.validators.UserValidator;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepo;
	private final UserValidator userValidator;

	public Result<User> addUser(User newUser) {
		newUser.setCredit(0);

		Result<User> validationResult = userValidator.validate(newUser);
		if (validationResult.isFailure())
			return validationResult;

		return userRepo.add(newUser);
	}

	Result<Boolean> isAdmin(String username) {
		Result<User> result = userRepo.find(username);
		if (result.isFailure())
			return Result.failureOf(result.getException());

		return Result.successOf(result.getData().getRole().equals(Admin));
	}
}

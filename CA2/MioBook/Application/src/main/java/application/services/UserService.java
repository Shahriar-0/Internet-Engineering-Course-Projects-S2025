package application.services;

import static domain.entities.User.Role.ADMIN;

import application.dtos.AddUserDto;
import application.repositories.IUserRepository;
import application.result.Result;
import application.validators.UserValidator;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepo;
	private final UserValidator userValidator;

	public Result<User> addUser(AddUserDto newUserDto) {
		Result<AddUserDto> validationResult = userValidator.validate(newUserDto);
		if (validationResult.isFailure())
			return Result.failureOf(validationResult.getException());

		User newUser = createUser(newUserDto);
		newUser.setCredit(0);
		return userRepo.add(newUser);
	}

	Result<Boolean> isAdmin(String username) {
		Result<User> result = userRepo.find(username);
		if (result.isFailure())
			return Result.failureOf(result.getException());

		return Result.successOf(result.getData().getRole().equals(ADMIN));
	}

	private User createUser(AddUserDto dto) {
		return User
			.builder()
			.key(dto.username())
			.address(dto.address())
			.password(dto.password())
			.email(dto.email())
			.role(User.Role.valueOf(dto.role().toUpperCase()))
			.build();
	}
}

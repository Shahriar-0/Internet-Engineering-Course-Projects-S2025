package application.services;

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
			return new Result<>(validationResult);

		User newUser = createUser(newUserDto);
		newUser.setCredit(0);
		return userRepo.add(newUser);
	}

	Result<User> doesExist(String username) {
		return userRepo.find(username);
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

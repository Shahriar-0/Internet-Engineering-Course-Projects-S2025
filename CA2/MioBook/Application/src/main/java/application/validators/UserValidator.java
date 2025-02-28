package application.validators;

import application.dtos.AddUserDto;
import application.exceptions.businessexceptions.userexceptions.*;
import application.repositories.IUserRepository;
import application.result.Result;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator implements IBaseValidator<AddUserDto> {

	private final IUserRepository userRepo;

	@Override
	public Result<AddUserDto> validate(AddUserDto userDto) {
		if (userRepo.exists(userDto.username()))
			return Result.failure(new UsernameAlreadyExists(userDto.username()));

		if (userRepo.doesEmailExist(userDto.email()))
			return Result.failure(new EmailAlreadyExists(userDto.email()));

		return Result.success(userDto);
	}
}

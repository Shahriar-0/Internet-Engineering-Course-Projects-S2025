package application.validators;

import application.dtos.AddUserDto;
import application.exceptions.businessexceptions.userexceptions.*;
import application.repositories.IUserRepository;
import application.result.Result;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator implements IBaseValidator<AddUserDto> {

	private final IUserRepository userRepo;

	/**
	 * Checks if a user with the given username already exists, or if the given email address is already in use.
	 *
	 * @param userDto The new user to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserException}. The only
	 *         possible exceptions are an {@link application.exceptions.businessexceptions.userexceptions.UsernameAlreadyExists} if a user with the same username already exists, or an
	 *         {@link application.exceptions.businessexceptions.userexceptions.EmailAlreadyExists} if the given email address is already in use.
	 */
	@Override
	public Result<AddUserDto> validate(AddUserDto userDto) {
		if (userRepo.exists(userDto.username()))
			return Result.failure(new UsernameAlreadyExists(userDto.username()));

		if (userRepo.doesEmailExist(userDto.email()))
			return Result.failure(new EmailAlreadyExists(userDto.email()));

		return Result.success(userDto);
	}
}

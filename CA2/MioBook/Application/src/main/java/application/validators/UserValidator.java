package application.validators;

import application.exceptions.userexceptions.*;
import application.repositories.IUserRepository;
import application.result.Result;
import domain.entities.User;

import java.util.regex.Pattern;

public class UserValidator implements IBaseValidator<User> {
    private final IUserRepository userRepo;
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]+$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final int MINIMUM_PASSWORD_LENGTH = 4;

    public UserValidator(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private boolean isUsernameFormatValid(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        return pattern.matcher(username).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= MINIMUM_PASSWORD_LENGTH;
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    @Override
    public Result<User> validate(User user) {
        if (userRepo.doesEmailExist(user.getEmail()))
            return Result.failureOf(new EmailAlreadyExists());

        if (!isUsernameFormatValid(user.getUsername()))
            return Result.failureOf(new InvalidUsernamePattern());

        if (!isPasswordValid(user.getPassword()))
            return Result.failureOf(new InvalidPasswordPattern());

        if (!isEmailValid(user.getEmail()))
            return Result.failureOf(new InvalidEmailPattern());

        return Result.successOf(user);
    }
}

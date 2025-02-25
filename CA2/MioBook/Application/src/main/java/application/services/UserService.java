package application.services;

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
}

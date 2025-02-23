package application.services;

import application.repositories.IUserRepository;
import application.response.Response;
import application.validators.UserValidator;
import domain.entities.User;

public class UserService {
    private final IUserRepository userRepo;
    private final UserValidator userValidator;

    public UserService(IUserRepository userRepo, UserValidator userValidator) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
    }

    public Response<User> addUser(User newUser) {
        Response<User> validationResponse = userValidator.validate(newUser);

        if (validationResponse.isFailure()) {
            return validationResponse;
        }

        return userRepo.add(newUser);
    }
}

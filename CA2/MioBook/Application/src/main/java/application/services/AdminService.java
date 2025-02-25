package application.services;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.result.Result;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;

    public Result<Author> addAuthor(Author newAuthor, String adminUsername) {
        Result<Boolean> isAdminResult = userService.isAdmin(adminUsername);
        if (isAdminResult.isFailure())
            return Result.failureOf(isAdminResult.getException());
        else if (!isAdminResult.getData())
            return Result.failureOf(new InvalidAccess());

        return null;
    }
}

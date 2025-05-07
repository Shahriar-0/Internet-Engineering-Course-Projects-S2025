package webapi.controllers;

import application.exceptions.BaseException;
import application.exceptions.businessexceptions.userexceptions.EmailAlreadyExists;
import application.exceptions.businessexceptions.userexceptions.UsernameAlreadyExists;
import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.user.GetUser;
import application.usecase.user.account.CreateAccount;
import domain.entities.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.UseCaseService;
import webapi.views.user.UserView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
	private static final String USER_ADDED = "User added successfully.";


	private final UseCaseService useCaseService;

	@PostMapping
	@Access(isWhiteList = false)
	public Response<?> addUser(@Valid @RequestBody CreateAccount.AddUserData data) {
		CreateAccount useCase = (CreateAccount) useCaseService.getUseCase(UseCaseType.CREATE_ACCOUNT);

		Result<User> result = useCase.perform(data);
		if (result.isFailure())
			return processFailureOfAddUser(result.exception());

		return Response.of(CREATED, USER_ADDED);
	}

	@GetMapping("/{username}")
	@Access(isWhiteList = false)
	public Response<UserView> getUser(@PathVariable String username) {
		GetUser useCase = (GetUser) useCaseService.getUseCase(UseCaseType.GET_USER);

		Result<User> result = useCase.perform(username);
		if (result.isFailure())
			throw result.exception();

		return Response.of(new UserView(result.data()), OK);
	}

    private Response<?> processFailureOfAddUser(BaseException exception) {
        Map<String, String> errors = new HashMap<>();
        if (exception instanceof UsernameAlreadyExists)
            errors.put("username", exception.getMessage());
        else if (exception instanceof EmailAlreadyExists)
            errors.put("email", exception.getMessage());
        else
            throw exception;

        return Response.of(errors, BAD_REQUEST, exception.getMessage());
    }
}

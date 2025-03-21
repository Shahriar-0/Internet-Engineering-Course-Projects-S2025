package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.user.account.CreateAccount;
import application.usecase.user.GetUser;
import domain.entities.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.response.Response;
import webapi.services.UseCaseService;
import webapi.views.user.UserView;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
	private static final String USER_ADDED = "User added successfully.";


	private final UseCaseService useCaseService;

	@PostMapping
	public Response<?> addUser(@Valid @RequestBody CreateAccount.AddUserData data) {
		CreateAccount useCase = (CreateAccount) useCaseService.getUseCase(UseCaseType.CREATE_ACCOUNT);
		Result<User> result = useCase.perform(data);
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, USER_ADDED);
	}

	@GetMapping("/{username}")
	public Response<UserView> getUser(@NotBlank @PathVariable String username) {
		GetUser useCase = (GetUser) useCaseService.getUseCase(UseCaseType.GET_USER);
		Result<User> result = useCase.perform(username);
		if (result.isFailure())
			throw result.exception();


		return Response.of(new UserView(result.data()), OK);
	}
}

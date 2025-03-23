package webapi.controllers;

import application.exceptions.BaseException;
import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.exceptions.businessexceptions.userexceptions.WrongPassword;
import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.user.account.Login;
import domain.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private static final String LOGIN_MESSAGE = "User logged in successfully";
	private static final String LOGOUT_MESSAGE = "User logged out successfully";
	private static final String WRONG_USERNAME_OR_PASSWORD_MESSAGE = "Username/Email or Password is not correct";


	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping("login")
	@Access(isWhiteList = false)
	public Response<?> login(@Valid @RequestBody Login.LoginData data) {
		authenticationService.validateNoOneLoggedIn();

		Login useCase = (Login) useCaseService.getUseCase(UseCaseType.LOGIN);
		Result<User> userResult = useCase.perform(data);
		if (userResult.isFailure())
			return processFailureOfLogin(userResult.exception());

		authenticationService.setLoggedInUser(userResult.data());
		return Response.of(OK, LOGIN_MESSAGE);
	}

	@DeleteMapping("logout")
	@Access(isWhiteList = false)
	public Response<?> logout() {
		authenticationService.validateSomeOneLoggedIn();
		authenticationService.unSetLoggedInUser();
		return Response.of(OK ,LOGOUT_MESSAGE);
	}

	private Response<?> processFailureOfLogin(BaseException exception) {
		if (!(exception instanceof UserNotFound || exception instanceof WrongPassword))
			throw exception;

		return Response.of(UNAUTHORIZED, WRONG_USERNAME_OR_PASSWORD_MESSAGE);
	}
}

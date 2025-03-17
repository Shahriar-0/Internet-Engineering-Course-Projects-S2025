package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.user.AddUserUseCase;
import application.uscase.user.GetUserUseCase;
import domain.entities.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.UseCaseService;
import webapi.views.user.UserView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	private final UseCaseService useCaseService;

	@PostMapping
	public ResponseEntity<String> addUser(@Valid @RequestBody AddUserUseCase.AddUserData data) {
		AddUserUseCase useCase = (AddUserUseCase) useCaseService.getUseCase(UseCaseType.ADD_USER);
		Result<User> result = useCase.perform(data);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("User added successfully.");
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserView> getUser(@NotBlank @RequestParam String username) {
		GetUserUseCase useCase = (GetUserUseCase) useCaseService.getUseCase(UseCaseType.GET_USER);
		Result<User> result = useCase.perform(username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new UserView(result.getData()));
	}
}

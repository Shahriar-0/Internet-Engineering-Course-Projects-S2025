package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.user.LoginUseCase;
import domain.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UseCaseService useCaseService;
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUseCase.LoginData data) {
        LoginUseCase useCase = (LoginUseCase) useCaseService.getUseCase(UseCaseType.LOGIN);
        Result<User> userResult = useCase.perform(data);
        if (userResult.isFailure())
            throw userResult.getException();

        authenticationService.setLoggedInUser(userResult.getData());
        return ResponseEntity.ok("User logged in successfully");
    }
}

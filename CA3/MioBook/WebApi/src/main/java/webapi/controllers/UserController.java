package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.user.AddUserUseCase;
import domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.services.UseCaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UseCaseService useCaseService;

    @PostMapping
    public ResponseEntity<String> addUser(AddUserUseCase.AddUserData data) {
        AddUserUseCase useCase = (AddUserUseCase) useCaseService.getUseCase(UseCaseType.ADD_USER);
        Result<User> result = useCase.perform(data);
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("User added successfully.");
    }
}

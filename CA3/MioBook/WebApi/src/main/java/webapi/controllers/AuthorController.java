package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.admin.AddAuthorUseCase;
import domain.entities.Author;
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
@RequestMapping("/author")
public class AuthorController {
    private final UseCaseService useCaseService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> addAuthor(@Valid @RequestBody AddAuthorUseCase.AddAuthorData data) {
        authenticationService.validateSomeOneLoggedIn();

        AddAuthorUseCase useCase = (AddAuthorUseCase) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
        Result<Author> result = useCase.perform(data, authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Author added successfully.");
    }
}

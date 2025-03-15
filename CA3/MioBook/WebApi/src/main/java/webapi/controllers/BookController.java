package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.admin.AddBookUseCase;
import domain.entities.Book;
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
@RequestMapping("/book")
public class BookController {
    private final UseCaseService useCaseService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> addBook(@Valid @RequestBody AddBookUseCase.AddBookData data) {
        authenticationService.validateSomeOneLoggedIn();

        AddBookUseCase useCase = (AddBookUseCase) useCaseService.getUseCase(UseCaseType.ADD_BOOK);
        Result<Book> result = useCase.perform(data, authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Book added successfully.");
    }
}

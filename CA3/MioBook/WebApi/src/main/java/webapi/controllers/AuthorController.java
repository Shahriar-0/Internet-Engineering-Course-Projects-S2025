package webapi.controllers;

import application.dtos.AddAuthorDto;
import application.result.Result;
import application.services.AdminService;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<String> addAuthor(AddAuthorDto addAuthorDto) {
        Result<Author> result = adminService.addAuthor(addAuthorDto);
        return ResponseEntity.ok(result.getException().getMessage());
    }
}

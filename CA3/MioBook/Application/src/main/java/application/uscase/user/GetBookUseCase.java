package application.uscase.user;

import application.repositories.IBookRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBookUseCase implements IUseCase {
    private final IBookRepository bookRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_BOOK;
    }

    public Result<Book> perform(String title) {
        assert title != null && !title.isBlank():
                "we relay on @NotBlank validation on title field in presentation layer";

        return bookRepository.find(title);
    }
}

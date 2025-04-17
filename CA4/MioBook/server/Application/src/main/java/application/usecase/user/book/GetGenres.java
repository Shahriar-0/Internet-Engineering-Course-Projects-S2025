package application.usecase.user.book;

import application.repositories.IBookRepository;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetGenres implements IUseCase {

    private final IBookRepository bookRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_GENRES;
    }

    public List<String> perform() {
        return bookRepository.getGenres();
    }
}

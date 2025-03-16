package webapi.views.book;

import application.page.Page;
import domain.entities.Book;

import java.util.List;

public record BookView(
        String title,
        String author,
        String publisher,
        List<String> genres,
        Integer year,
        Long price,
        String synopsis,
        Float averageRating
)
{
    public BookView(Book entity) {
        this (
                entity.getTitle(),
                entity.getAuthorName(),
                entity.getPublisher(),
                entity.getGenres(),
                entity.getYear(),
                entity.getPrice(),
                entity.getSynopsis(),
                entity.getAverageRating()
        );
    }

    public static Page<BookView> mapToView(Page<Book> bookPage) {
        return Page.<BookView>builder()
                .data(bookPage.getData().stream().map(BookView::new).toList())
                .pageNumber(bookPage.getPageNumber())
                .pageSize(bookPage.getPageSize())
                .totalPageNumber(bookPage.getTotalPageNumber())
                .totalDataSize(bookPage.getTotalDataSize())
                .build();
    }
}

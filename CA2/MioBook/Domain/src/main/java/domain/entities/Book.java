package domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Book extends DomainEntity<String>{

    private Author author;
    private String publisher;
    private String publishedYear; // TODO: find better name for this
    private long price;
    private String synopsis;
    private String content;

    public String getTitle() {
        return super.getKey();
    }

    public Book(String title, Author author, String publisher, String publishedYear, long price, String synopsis, String content) {
        super(title);
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.price = price;
        this.synopsis = synopsis;
        this.content = content;
    }
}

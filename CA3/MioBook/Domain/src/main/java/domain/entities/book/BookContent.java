package domain.entities.book;

import domain.entities.DomainEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BookContent extends DomainEntity<String> {
    private String content;
    public String getTitle() {
        return key;
    }

    public BookContent(String title, String content) {
        super(title);
        this.content = content;
    }
}

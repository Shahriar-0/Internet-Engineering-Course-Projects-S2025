package domain.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {
    private String name;
    private String penName;
    private LocalDate born;
    private LocalDate death;
    public String getUsername() {
        return super.getKey();
    }

    public Author(String username, String name, String penName, LocalDate born, LocalDate death) {
        super(username);
        this.name = name;
        this.penName = penName;
        this.born = born;
        this.death = death;
    }
}

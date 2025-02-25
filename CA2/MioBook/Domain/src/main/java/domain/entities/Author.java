package domain.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {
    public String getName() {
        return super.getKey();
    }
    private String penName;
    private String nationality;
    private LocalDate born;
    private LocalDate died;

    public Author(String name, String penName, String nationality, LocalDate born, LocalDate death) {
        super(name);
        this.penName = penName;
        this.nationality = nationality;
        this.born = born;
        this.died = death;
    }
}

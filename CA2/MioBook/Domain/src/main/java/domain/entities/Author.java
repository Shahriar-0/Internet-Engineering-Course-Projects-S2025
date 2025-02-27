package domain.entities;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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

	public Author(String name, String penName, String nationality, LocalDate born, LocalDate died) {
		super(name);
		this.penName = penName;
		this.nationality = nationality;
		this.born = born;
		this.died = died;
	}
}

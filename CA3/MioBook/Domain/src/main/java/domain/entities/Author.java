package domain.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {

	private String penName;
	private String nationality;
	private LocalDate born;
	private LocalDate died;

	@Override
	public String getKey() {
		return super.getKey();
	}

	public String getName() {
		return super.getKey();
	}
}

package domain.entities;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Author extends DomainEntity<String> {

	@Override
	public String getKey() {
		return super.getKey();
	}

	public String getName() {
		return super.getKey();
	}

	private String penName;
	private String nationality;

	private LocalDate born;

	private LocalDate died;
}

package domain.entities;

import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class User extends DomainEntity<String> {

	public enum Role {
		CUSTOMER,
		ADMIN,
	}

	protected Address address;
	protected String password;
	protected String email;
	protected Role role;
	protected long credit;

	public String getUsername() {
		return super.getKey();
	}
}

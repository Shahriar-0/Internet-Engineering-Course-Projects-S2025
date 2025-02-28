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

	private Address address;
	private String password;
	private String email;
	private Role role;
	private long credit;

	public String getUsername() {
		return super.getKey();
	}
}

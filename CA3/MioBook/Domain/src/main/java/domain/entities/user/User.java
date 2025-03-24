package domain.entities.user;

import domain.entities.DomainEntity;
import domain.valueobjects.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class User extends DomainEntity<String> {

	protected Address address;
	protected String password;
	protected String email;
	protected Role role;
	public String getUsername() {
		return super.getKey();
	}

	public User(String username, String password, String email, Address address, Role role) {
		super(username);
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
	}
}

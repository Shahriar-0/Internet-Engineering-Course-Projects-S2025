package domain.entities.user;

import domain.entities.DomainEntity;
import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class User extends DomainEntity {

	protected String username;
    protected Address address;
	protected String password;
	protected String email;
	protected Role role;
	protected String salt;

	public User(String username, String password, String salt, String email, Address address, Role role) {
		this.username = username;
        this.password = password;
        this.salt = salt;
		this.email = email;
		this.address = address;
		this.role = role;
	}
}

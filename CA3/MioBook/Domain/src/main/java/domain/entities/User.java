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

    public String getUsername() {
        return super.getKey();
    }

    @Override
    public String getKey() {
        return super.getKey();
    }


    public String getRoleAsString() {
        return role.name().toLowerCase();
    }

    public String getPassword() {
        return password;
    }
}

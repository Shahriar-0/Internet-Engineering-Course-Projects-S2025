package domain.entities;

import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

// TODO: create a package for user and move this and customer and admin there, also make role a separate enum
@Getter
@Setter
@SuperBuilder
public abstract class User extends DomainEntity<String> {

	@Getter
	public enum Role {
		CUSTOMER("customer"),
		ADMIN("admin"),
		NONE("none");

		private final String value;

		Role(String value) {
			this.value = value;
		}

		public static Role getByValue(String value) {
			for (Role role : Role.values()) {
				if (role.value.equals(value))
					return role;
			}
			return NONE;
		}
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

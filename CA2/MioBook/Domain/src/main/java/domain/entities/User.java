package domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("username")
    public String getUsername() {
        return super.getKey();
    }

    @Override
    @JsonIgnore
    public String getKey() {
        return super.getKey();
    }


    @JsonProperty("role")
    public String getRoleAsString() {
        return role.name().toLowerCase();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}

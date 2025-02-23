package domain.entities;

import domain.valueobject.Address;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class User extends DomainEntity<String> {
    public enum Role {
        Customer,
        Admin
    }

    private Address address;
    private String password;
    private String email;
    private Role role;
    private long credit;
    public String getUsername() {
        return super.getKey();
    }

    public User(String username, String password, String email, Address address, Role role, long credit) {
        super(username);
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.credit = credit;
    }
}

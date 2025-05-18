package domain.entities.user;

import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Admin extends User {
    public Admin(String username, String password, String salt, String email, Address address) {
        super(username, password, salt, email, address, Role.ADMIN);
    }
}

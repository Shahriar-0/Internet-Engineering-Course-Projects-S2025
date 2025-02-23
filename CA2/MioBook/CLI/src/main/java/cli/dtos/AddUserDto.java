package cli.dtos;

import domain.entities.User;
import domain.valueobject.Address;
import lombok.Data;

@Data
public class AddUserDto {
    private User.Role role;
    private String username;
    private String password;
    private String email;
    private Address address;
}

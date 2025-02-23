package cli.command;

import application.response.Response;
import application.services.UserService;
import cli.dtos.AddUserDto;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddUserCommand implements BaseCommand {
    private static final String CUSTOMER_ROLE = "customer";
    private static final String ADMIN_ROLE = "admin";

    private final AddUserDto addUserDto;
    private final UserService userService;

    @Override
    public void execute() {
        Response<User> response = userService.addUser(createUser(addUserDto));
        System.out.println(response.getData());
    }

    private User createUser(AddUserDto dto) {
        return User.builder()
                .key(dto.username())
                .address(dto.address())
                .password(dto.password())
                .email(dto.email())
                .role(getRole(dto.role()))
                .build();
    }

    private User.Role getRole(String role) {
        return switch (role) {
            case CUSTOMER_ROLE -> User.Role.Customer;
            case ADMIN_ROLE -> User.Role.Admin;
            default -> throw new IllegalArgumentException("Invalid user role");
        };
    }
}

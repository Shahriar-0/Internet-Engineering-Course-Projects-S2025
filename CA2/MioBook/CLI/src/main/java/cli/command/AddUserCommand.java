package cli.command;

import application.services.UserService;
import cli.dtos.AddUserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddUserCommand implements BaseCommand {

    private final AddUserDto addUserDto;
    private final UserService userService;

    @Override
    public void execute() {
        // userService.addUser(addUserDto);
        System.out.println("Add user with dto: " + addUserDto);
    }
}

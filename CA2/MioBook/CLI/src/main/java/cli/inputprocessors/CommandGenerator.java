package cli.inputprocessors;

import cli.command.AddUserCommand;
import cli.command.BaseCommand;
import cli.command.CommandType;
import cli.dtos.AddUserDto;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.services.UserService;


@Service
public class CommandGenerator {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    
    public CommandGenerator(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    public BaseCommand generateCommand(String input) {
        try {
            String jsonString = input.substring(input.indexOf(" ") + 1);

            return switch (CommandType.valueOf(input.split(" ")[0])) {
                case ADD_USER -> new AddUserCommand(objectMapper.readValue(jsonString, AddUserDto.class), userService);
                case ADD_BOOK -> null;
            };

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input format: " + e.getMessage(), e);
        }
    }
}

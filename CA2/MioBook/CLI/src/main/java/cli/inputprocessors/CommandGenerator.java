package cli.inputprocessors;

import application.services.AdminService;
import application.services.UserService;
import cli.command.AddAuthorCommand;
import cli.command.AddUserCommand;
import cli.command.IBaseCommand;
import cli.command.CommandType;
import cli.dtos.AddAuthorDto;
import cli.dtos.AddUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandGenerator {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserService userService;
    private final AdminService adminService;

    public IBaseCommand generateCommand(String input) throws JsonProcessingException, IllegalArgumentException {
        String jsonString = input.substring(input.indexOf(" ") + 1);

        return switch (CommandType.valueOf(input.split(" ")[0].toUpperCase())) {
            case ADD_USER -> new AddUserCommand(objectMapper.readValue(jsonString, AddUserDto.class), userService);
            case ADD_AUTHOR -> new AddAuthorCommand(objectMapper.readValue(jsonString, AddAuthorDto.class), adminService);
        };
    }
}

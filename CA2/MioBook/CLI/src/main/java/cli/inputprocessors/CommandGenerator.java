package cli.inputprocessors;

import cli.command.AddUserCommand;
import cli.command.BaseCommand;
import cli.command.CommandType;
import cli.dtos.AddUserDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
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

    public BaseCommand generateCommand(String input) throws JsonProcessingException, IllegalArgumentException {
        String jsonString = input.substring(input.indexOf(" ") + 1);

        return switch (CommandType.valueOf(input.split(" ")[0].toUpperCase())) {
            case ADD_USER -> new AddUserCommand(objectMapper.readValue(jsonString, AddUserDto.class), userService);
        };
    }
}

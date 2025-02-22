package cli.inputprocessors;

import cli.command.AlakiCommand;
import cli.command.BaseCommand;
import org.springframework.stereotype.Service;

@Service
public class CommandGenerator {
    public BaseCommand generateCommand(String input) {
        return new AlakiCommand();
    }
}

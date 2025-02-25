package cli;

import cli.command.BaseCommand;
import cli.configuration.AppContext;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import cli.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Runner {
    private static final AppContext context = new AppContext();
    private final CommandGenerator commandGenerator;
    private final CliWriter cliWriter;

    public Runner(CommandGenerator commandGenerator, CliWriter cliWriter) {
        this.commandGenerator = commandGenerator;
        this.cliWriter = cliWriter;
    }

    public static void main(String[] args) {
        Runner runner = new Runner(context.getCommandGenerator(), context.getCliWriter());
        runner.run(args);
    }

    private void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                BaseCommand command = commandGenerator.generateCommand(scanner.nextLine());
                Response response = command.execute();
                cliWriter.writeResponseToConsole(response);
            }
            catch (IllegalArgumentException | JsonProcessingException exception) {
                //TODO: Add response here
                System.out.println(exception.getMessage());
            }
        }
        scanner.close();
    }
}

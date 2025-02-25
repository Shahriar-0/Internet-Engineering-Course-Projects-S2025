package cli;

import cli.command.BaseCommand;
import cli.configuration.AppContext;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import cli.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class Runner {
    private static final AppContext context = new AppContext();

    private final CommandGenerator commandGenerator;
    private final CliWriter cliWriter;

    public static void main(String[] args) throws JsonProcessingException {
        Runner runner = new Runner(context.getCommandGenerator(), context.getCliWriter());
        runner.run(args);
    }

    private void run(String... args) throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                BaseCommand command = commandGenerator.generateCommand(scanner.nextLine());
                Response response = command.execute();
                cliWriter.writeResponseToConsole(response);
            }
            catch (Exception e) {
                Response response = new Response(false, e.getMessage(), null);
                cliWriter.writeResponseToConsole(response);
            }
        }
        scanner.close();
    }
}

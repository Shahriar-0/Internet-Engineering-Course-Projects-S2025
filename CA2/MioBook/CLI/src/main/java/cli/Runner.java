package cli;

import cli.command.IBaseCommand;
import cli.configuration.AppContext;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import cli.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

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
			Response response = null;
			try {
				IBaseCommand command = commandGenerator.generateCommand(scanner.nextLine());
				response = command.execute();
			}
			catch (JsonProcessingException e) {
				response = new Response(false, e.getOriginalMessage(), null);
			}
			catch (Exception e) {
				response = new Response(false, e.getMessage(), null);
			}
			finally {
				cliWriter.writeResponseToConsole(response);
			}
		}
		scanner.close();
	}
}

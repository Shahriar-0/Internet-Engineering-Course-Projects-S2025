package cli;

import cli.command.BaseCommand;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import cli.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Runner implements CommandLineRunner {
    private final CommandGenerator commandGenerator;
    private final CliWriter cliWriter = new CliWriter();

    public Runner(CommandGenerator commandGenerator) {
        this.commandGenerator = commandGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) {
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

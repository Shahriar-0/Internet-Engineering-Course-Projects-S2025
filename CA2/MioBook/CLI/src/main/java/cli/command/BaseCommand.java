package cli.command;

import cli.response.Response;

public interface BaseCommand {
    Response execute();
}

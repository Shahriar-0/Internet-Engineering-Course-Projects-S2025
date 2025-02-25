package cli.command;

import application.result.Result;
import cli.response.Response;

public interface BaseCommand {
    Response execute();
}

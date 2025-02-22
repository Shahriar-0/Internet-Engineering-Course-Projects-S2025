package cli.inputprocessors;

import application.services.AlakiService;
import application.services.Falaki;
import application.services.Moftaki;
import cli.command.AlakiCommand;
import cli.command.BaseCommand;
import org.springframework.stereotype.Service;

@Service
public class CommandGenerator {
    private final AlakiService alakiService;
    private final Falaki falaki;
    private final Moftaki moftaki;

    public CommandGenerator(AlakiService alakiService, Falaki falaki, Moftaki moftaki) {
        this.alakiService = alakiService;
        this.falaki = falaki;
        this.moftaki = moftaki;
    }

    public BaseCommand generateCommand(String input) {
        moftaki.salam();
        falaki.salam();
        return new AlakiCommand(alakiService);
    }
}

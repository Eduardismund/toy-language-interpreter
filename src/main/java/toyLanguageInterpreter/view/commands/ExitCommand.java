package toyLanguageInterpreter.view.commands;

import toyLanguageInterpreter.exceptions.ViewException;

public class ExitCommand extends Command {
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() throws ViewException {
        System.exit(0);
    }
}

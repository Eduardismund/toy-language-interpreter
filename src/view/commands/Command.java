package view.commands;

import exceptions.ViewException;

public abstract class Command {
    private String key;
    private String description;
    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }
    public abstract void execute() throws ViewException;
    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}

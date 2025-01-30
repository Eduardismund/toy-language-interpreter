package toyLanguageInterpreter.view;

import toyLanguageInterpreter.exceptions.ViewException;
import toyLanguageInterpreter.view.commands.ClearFile;
import toyLanguageInterpreter.view.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    private final Map<String, Boolean> executedCommands;

    public TextMenu() {
        commands = new HashMap<>();
        executedCommands = new HashMap<>();
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu(){
        System.out.println(" \n----- INTERPRETER COMMANDS ----- ");
        for(Command com: commands.values()) {
            String line = String.format("%4s: %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws ViewException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Option > ");
            String line = scanner.nextLine();
            switch(line){
                case "1" -> ClearFile.clear("log1.txt");
                case "2" -> ClearFile.clear("log2.txt");
                case "3" -> ClearFile.clear("log3.txt");
                case "4" -> ClearFile.clear("log4.txt");
                case "5" -> ClearFile.clear("log5.txt");
                case "6" -> ClearFile.clear("log6.txt");
                case "7" -> ClearFile.clear("log7.txt");
                case "8" -> ClearFile.clear("log8.txt");

            }
            Command c = commands.get(line);
            if(c == null) {
                System.out.println("Invalid option");
                continue;
            }
            c.execute();

//            executedCommands.put(line, true);
        }
    }
}

import Repository.IRepository;
import Repository.Repository;
import controller.Controller;
import model.adt.*;
import model.statements.IStmt;
import model.PrgState;
import model.values.Value;
import view.TextMenu;
import exceptions.ViewException;
import view.commands.Examples;
import Repository.RepoException;
import view.commands.ExitCommand;
import view.commands.RunCommand;


public class Main {
    public static void main(String[] args) {
        IStmt[] examples = Examples.exampleList();

        try {
            Controller controller1 = setController(examples[0], "log1.txt");
            Controller controller2 = setController(examples[1], "log2.txt");
            Controller controller3 = setController(examples[2], "log3.txt");
            Controller controller4 = setController(examples[3], "log4.txt");
            Controller controller5 = setController(examples[4], "log5.txt");
            Controller controller6 = setController(examples[5], "log6.txt");
            Controller controller7 = setController(examples[6], "log7.txt");
            Controller controller8 = setController(examples[7], "log8.txt");
            Controller controller9 = setController(examples[8], "log9.txt");


            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunCommand("1", examples[0].toString(), controller1));
            menu.addCommand(new RunCommand("2", examples[1].toString(), controller2));
            menu.addCommand(new RunCommand("3", examples[2].toString(), controller3));
            menu.addCommand(new RunCommand("4", examples[3].toString(), controller4));
            menu.addCommand(new RunCommand("5", examples[4].toString(), controller5));
            menu.addCommand(new RunCommand("6", examples[5].toString(), controller6));
            menu.addCommand(new RunCommand("7", examples[6].toString(), controller7));
            menu.addCommand(new RunCommand("8", examples[7].toString(), controller8));
            menu.addCommand(new RunCommand("9", examples[8].toString(), controller9));

            menu.show();
        } catch (RepoException | ViewException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Controller setController(IStmt example, String filename) throws RepoException {
        PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), example, new MyDictionary<>(), new MyHeap<>() );
        IRepository repo = new Repository(filename);
        repo.add(prgState);
        return new Controller(repo);
    }

}
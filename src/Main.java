import Repository.IRepository;
import Repository.Repository;
import controller.Controller;
import model.adt.MyFilename;
import model.statements.IStmt;
import model.PrgState;
import model.adt.MySymTable;
import model.adt.MyOut;
import model.adt.MyExeStack;
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
            IStmt example1 = examples[0];
            PrgState prgState1 = new PrgState(new MyExeStack<>(), new MySymTable<>(), new MyOut<>(), example1, new MyFilename<>());
            IRepository repo1 = new Repository("log1.txt");
            repo1.add(prgState1);
            Controller controller1 = new Controller(repo1);

            IStmt example2 = examples[1];
            PrgState prgState2 = new PrgState(new MyExeStack<>(), new MySymTable<>(), new MyOut<>(), example2, new MyFilename<>());
            IRepository repo2 = new Repository("log2.txt");
            repo2.add(prgState2);
            Controller controller2 = new Controller(repo2);

            IStmt example3 = examples[2];
            PrgState prgState3 = new PrgState(new MyExeStack<>(), new MySymTable<>(), new MyOut<>(), example3, new MyFilename<>());
            IRepository repo3 = new Repository("log3.txt");
            repo3.add(prgState3);
            Controller controller3 = new Controller(repo3);

            IStmt example4 = examples[3];
            PrgState prgState4 = new PrgState(new MyExeStack<>(), new MySymTable<>(), new MyOut<>(), example4, new MyFilename<>());
            IRepository repo4 = new Repository("log4.txt");
            repo4.add(prgState4);
            Controller controller4 = new Controller(repo4);

            IStmt example5 = examples[4];
            PrgState prgState5 = new PrgState(new MyExeStack<>(), new MySymTable<>(), new MyOut<>(), example5, new MyFilename<>());
            IRepository repo5 = new Repository("log5.txt");
            repo5.add(prgState5);
            Controller controller5 = new Controller(repo5);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunCommand("1", example1.toString(), controller1));
            menu.addCommand(new RunCommand("2", example2.toString(), controller2));
            menu.addCommand(new RunCommand("3", example3.toString(), controller3));
            menu.addCommand(new RunCommand("4", example4.toString(), controller4));
            menu.addCommand(new RunCommand("5", example5.toString(), controller5));

            menu.show();
        } catch (RepoException | ViewException e) {
            System.out.println(e.getMessage());
        }
    }

}